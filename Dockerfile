FROM openjdk:11
COPY target/heycarlistings.jar heycarlistings.jar
ENTRYPOINT ["java","-jar","/heycarlistings.jar"]