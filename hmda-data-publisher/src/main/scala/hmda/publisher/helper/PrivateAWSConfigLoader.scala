package hmda.publisher.helper

import com.typesafe.config.ConfigFactory

trait PrivateAWSConfigLoader {
  val awsConfigPrivate = ConfigFactory.load("application.conf").getConfig("private-aws")
  val accessKeyIdPrivate  = awsConfigPrivate.getString("private-access-key-id")
  val secretAccessPrivate = awsConfigPrivate.getString("private-secret-access-key ")
  val regionPrivate       = awsConfigPrivate.getString("private-region")
  val bucketPrivate       = awsConfigPrivate.getString("private-s3-bucket")
  val environmentPrivate  = awsConfigPrivate.getString("private-environment")

  val awsCredentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyIdPrivate, secretAccessPrivate))

  val awsRegionProvider: AwsRegionProvider = () => Region.of(regionPrivate)

}
