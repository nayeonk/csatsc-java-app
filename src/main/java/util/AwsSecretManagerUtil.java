package util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import model.AwsSecret;

import java.util.Arrays;

public class AwsSecretManagerUtil {


    private static    final String AWS_KEY = System.getenv("AWS_KEY");
    private static final String AWS_SECRET = System.getenv("AWS_SECRET");
    private static final String ENVIRONMENT= System.getenv("ENVIRONMENT");
    private static Gson gson = new Gson();
    private static AwsSecret awsSecret=new AwsSecret();
    public static AwsSecret getAwsSecrets(){
        return awsSecret;
    }
    public static   AwsSecret fetch() {
        String secretName;
        if(ENVIRONMENT.equals(EnvironmentConstants.ENV_PRODUCTION)) {
            secretName = "prod/secret";
        } else if (ENVIRONMENT.equals(EnvironmentConstants.ENV_STAGING)) {
            secretName="staging/secret";
        }else {
            secretName = "dev/secret";
        }
        String region = "us-west-2";

        AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_KEY,AWS_SECRET )))
                .build();
        String secret, decodedBinarySecret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
            System.out.println("getSecretValueResult is: "+getSecretValueRequest);
        } catch (Exception e) {

            System.out.println("secret manager error: "+e.getMessage());

        }

        if (getSecretValueResult != null) {
            secret = getSecretValueResult.getSecretString();
            awsSecret=  gson.fromJson(secret, AwsSecret.class);
            return awsSecret;
        }


        return null;
    }
}
