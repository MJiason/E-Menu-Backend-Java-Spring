package com.MJaison.Emenu.repository;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.sun.el.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;

@Repository
public class AwsS3Storage  {
    private final String AWS_accesskey = "AKIAJR4XX5NGQGGXB52A";
    private final String AWS_secretkey = "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";
    private AmazonS3 s3client;
    private String bucketName = "e-menu-photo-storage";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AwsS3Storage() {

        AWSCredentials credentials = new BasicAWSCredentials(
                this.AWS_accesskey,
                this.AWS_secretkey
        );

        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();
    }




    public void save(String name, File img) throws NullPointerException{
        if (img.equals(null)) {
            throw new NullPointerException("Empty file");
        }
        s3client.putObject(bucketName, name, img);
    }

    public URL getUrl(String fileName) throws NullPointerException{
        URL url =  s3client.getUrl(bucketName, fileName);
        if(url.equals(null)) {
            throw new NullPointerException("URL not found");
        }
        return url;
    }


}
