package com.google.sps.servlets;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig()
public class ImageFormServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        //Generate unique ID
        UUID uuid = UUID.randomUUID();
        //Get message & files, potentially useful when storing images with tags?
        String message = request.getParameter("message");

        //Get the actual file
        Part filePart = request.getPart("image");   //This might have to be changed for each image?
                                                    //If one upload has many images?
        String fileName = uuid.toString();
        InputStream fileInputStream = filePart.getInputStream();
        
        //Upload file
        String uploadedFileUrl = uploadToCloudStorage(fileName, fileInputStream);

        PrintWriter out = response.getWriter();
        out.println("<p>Here's the image you uploaded!</p>");
        out.println("<a href=\"" + uploadedFileUrl + "\">");
        out.println("<img src=\"" + uploadedFileUrl + "\"");
        out.println("</a>");
        out.println(message);
    }

    /**
     * Takes an image with a filename and gives a media return
     * @param fileName File Name
     * @param inputStream Assuming this is the image being streamed in
     * @return Link to file (URL)
     */
    private static String uploadToCloudStorage(String fileName, InputStream inputStream) {
        String projectID = "jgreene-sps-spring21";
        String bucketName ="jgreene-sps-spring21.appspot.com";
        Storage storage = 
            StorageOptions.newBuilder().setProjectId(projectID).build().getService();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, inputStream);

        return blob.getMediaLink();

    }
}