FROM openjdk:11

COPY target/pharmacy-product-system.jar pharmacy-product-system.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "pharmacy-product-system.jar"]
