# Read Me
This is an initial implementation for the case assesment image service.

Run the application with default port(8080):
> mvn spring-boot:run

There are couple of things that are not implemented for the sake of time:
- A proper integration with AWS S3 is missing, instead a FakeS3Client used which mocks the S3 behaviour using a simple map.
- Resizing and optimizing images are not implemented. The service related to this functionality simply returns original image.
- logdb configuration is missing
- Overall more tests should be written especially for ShowImageService and FLushImageService where the business logic is.