package com.google.sps.servlets;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * @return
     */
    private String toGson(Page<Blob> blobs) {
        Gson gson = new Gson();
        List<String> mediaLinks = new ArrayList<String>();
        //Slow but I think this is right
        for(Blob blob : blobs.iterateAll()) {
            mediaLinks.add(blob.getMediaLink());
        }
        String json = gson.toJson(mediaLinks);
        return json;
    }
}

