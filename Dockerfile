FROM openjdk:8
EXPOSE 8080
ENV database_url=http://localhost:8060
ENV auth_url=http://localhost:8070
COPY target/HotelService.jar HotelService.jar
CMD ["java", "-jar", "HotelService.jar"]
