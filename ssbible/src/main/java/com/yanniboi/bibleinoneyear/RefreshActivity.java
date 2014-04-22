package com.yanniboi.bibleinoneyear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RefreshActivity extends Activity {

    /**
     * Database connection.
     */
    private Database db;
    public static String fileName = "nodes.json";
    public static int siteStatus = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_refresh);

        db = new Database(getApplicationContext());

        downloadContent();
    }


    public void downloadContent() {
        new DownloadContentTask().execute();
    }

    private class DownloadContentTask extends AsyncTask<Context, Integer, Boolean> {

        public int numberOfNodes = 0;
        public JSONArray nodes = null;


        protected Boolean doInBackground(Context... params) {
            try {
                // Download json file.
                try {
                    siteStatus = requestNodes();
                } catch (IOException e) {
                    // @todo Error handling
                }

                if (siteStatus == 200) {
                    // Parse json.
                    String json = new BufferedReader(new InputStreamReader(openFileInput(fileName), "UTF-8")).readLine();

                    try {
                        nodes = new JSONArray(json);
                    } catch (Exception ignored) {
                        // @todo error handling.
                    }

                    // Return early if necessary.
                    if (nodes == null) {
                        return false;
                    }

                    nodes = getNewNodes(nodes);

                    // Get number of sessions.
                    int count = nodes.length();

                    // Loop to create entries.
                    for (int i = 0; i < count; i++) {

                        JSONObject node = nodes.getJSONObject(i);
                        Entry page = new Entry();
                        page.setNid(node.getInt("nid"));
                        page.setTitle(node.getString("title"));
                        page.setAuthor("yanniboi");
                        db.addPage(page);
                        // Update progress
                        int update = (i * 100 / count);
                        publishProgress(update);
                        if (isCancelled()) break;

                    }

                    // Create database Entries.
                } else {
                    return false;
                }

                // Get number of nodes
            }
            catch (IOException ignored) {
                // @todo error handling.
            }
            catch (JSONException e) {
                // @todo error handling.
            }
            return true;
        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            //dialog.setProgress(progress[0]);
        }

        protected void onPostExecute(Boolean result) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            // Create a dialog fragment to show complete message.
            //showDialog("Downloaded " + result + " bytes");
            // Recreate the navigation fragment.
        }

    }

    /**
     * Download the program from the internet and save it locally.
     */
    public int requestNodes() throws IOException {
        siteStatus = -1;

        try {

            //URL downloadFileUrl = new URL("http://bible.soulsurvivor.com/phonegap/node.json");
            URL downloadFileUrl = new URL("http://bible.soulsurvivor.com/export");
            HttpURLConnection httpConnection = (HttpURLConnection) downloadFileUrl.openConnection();
            siteStatus = httpConnection.getResponseCode();
            if (siteStatus == 200) {
                InputStream inputStream = httpConnection.getInputStream();

                byte[] buffer = new byte[1024];
                int bufferLength;

                // Write data to local file.
                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                while ((bufferLength = inputStream.read(buffer)) > 0 ) {
                    fos.write(buffer, 0, bufferLength);
                }
                fos.flush();
                fos.close();
            }

            httpConnection.disconnect();
        }
        catch (IOException ignored) {}

        return siteStatus;
    }

    /**
     * Download the program from the internet and save it locally.
     */
    public JSONArray getNewNodes(JSONArray nodes) throws JSONException {
        JSONArray newNodes = new JSONArray();

        for (int i = 0; i < nodes.length(); i++) {
            JSONObject node = nodes.getJSONObject(i);
            int druaplId = node.getInt("nid");
            if (db.getPagebyNid(druaplId).getNid() == 0) {
                newNodes.put(node);
            }
            else {
                continue;
            }
        }

        return newNodes;
    }

}
