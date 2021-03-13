package com.google.sps.servlets;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/list-images")
public class ListImagesServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String projectID = "jgreene-sps-spring21";
        String bucketName ="jgreene-sps-spring21.appspot.com";
        Storage storage = StorageOptions.newBuilder().setProjectId(projectID).build().getService();
        Bucket bucket = storage.get(bucketName);
        Page<Blob> blobs = bucket.list(); //Is this an actual list?
        
        String json = toGson(blobs);
        response.setContentType("application/json");
        response.getWriter().println(json);
    }
    //Maybe create an inner class that holds the media URL as a string, tags as an array, and a string as a name?
    //Then output it to JSON?
    //Need to checkout blob methods
    /**
     * Adds a collection of blobs to a list and then converts list to JSON
     * @param blobs Collection of blobs, used to get media links.
     * @return Formatted JSON
     */
    private String toGson(Page<Blob> blobs) {
        Gson gson = new Gson();
        List<String> mediaLinks = new ArrayList<String>();
        //Slow but I think this is right
        List<ExportBlobInfo> blobsInfoList = new ArrayList<ExportBlobInfo>();

        List<Blob> blobsList = new ArrayList<Blob>();
        for(Blob blob : blobs.iterateAll()) {
            blobsList.add(blob);
            blobsInfoList.add(
                new ExportBlobInfo(blob.getMediaLink(), 
                blob.getMetadata() != null ? blob.getMetadata().get("tag") : null) //this is if metadata is somehow null, shouldn't be null after this image is deleted.
                );
        }
        System.out.println(blobsList.get(0));
        String json = gson.toJson(blobsInfoList);
        return json;
    }

    /**
     * Will look to see if there is another solution, perhaps putting a Map into a Map? This seems easier for JSON though.
     */
    private class ExportBlobInfo {
        private String mediaLink;
        private String tag;

        public ExportBlobInfo(String link, String t) {
            if (t == null) {
                tag = "No comment was entered."; //Perhaps this will be changed if I get image analysis running?
            } else {
                tag = t;
            }
            mediaLink = link;
        }

        /**
         * Sets Media link
         * @param link media link
         */
        public void setMediaLink(String link) {
            mediaLink = link;
        }
        /**
         * Sets tag
         * @param t tag
         */
        public void setTag(String t) {
            tag = t;
        }

        /**
         * Gets Media Link
         * @return media link
         */
        public String getMediaLink(){
            return mediaLink;
        }

        /**
         * Gets tag
         * @return tag
         */
        public String getTag(){
            return tag;
        }
    } 
}

