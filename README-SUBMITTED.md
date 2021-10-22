# Bring your own interview technical task  - Submitted Project

## Setup

To build and run the software:

```bash
./build.sh
docker-compose up --force-recreate
```

## Changes Made

### 01-initial-db.sql

The 2 data columns createdAt and updatedAt have been renamed to created_at and updated_at.

A new 'title' column has been added.

## Testing
Dashboard api service runs on the port 8081. Smoke tests are updated to run against this service.

```bash
# Smoke tests
./smoke_tests.sh

# List all data
curl 'http://localhost:8081/anaplan/dashboards' | jq .

# Get single record
curl 'http://localhost:8081/anaplan/2/dashboards' | jq .

# Add a new record
curl --request POST 'http://localhost:8081/anaplan/dashboards' \
--header 'content-type: application/json' \
--data-raw '{
  "title": "New Title"
}' | jq .

# Update the record
curl --location --request PUT 'http://localhost:8081/anaplan/6/dashboards' \
--header 'content-type: application/json' \
--data-raw '{
  "title": "New Title - updated"
}' | jq .

# Delete the record
curl --location --request DELETE 'http://localhost:8081/anaplan/6/dashboards'
```

## REST API's Swagger document
```
http://localhost:8081/swagger-ui.html#/dashboard-controller
http://localhost:8081/v2/api-docs
```

## Viewing data on mysql database
1. Login to the container CLI from Docker Desktop Dashboard

   ```mysql -uroot -ptest123```

    OR

   From the command line run the below

   ```docker run -it --network anaplan-tech_default --rm mysql:5 mysql -hanaplan-tech_mysql-db_1 -uroot -ptest123```

2. use definitions;
3. select * from dashboards;

## Explore manage endpoints
All the manage endpoints are available at http://localhost:8081/manage.
This endpoint is protected with HTTP Basic Authentication.
You can use the username admin and password admin for http basic authentication.

### Application Build information 
Application build information is exposed on the endpoint

```
http://localhost:8081/manage/info
```

Output

```
{
app: 
   {
      name: "bring-your-own-interview",
      description: "This is a Spring Boot Application for bring your own interview task.",
      copyright: "ABC Ltd"
   },
build: 
   {
      artifact: "bring-your-own-interview",
      name: "bring-your-own-interview",
      time: "2021-10-06T20:05:35.564Z",
      version: "0.0.1-SNAPSHOT",
      group: "com.anaplan"
   }
}
```


### Health Check
To check the status of the mysql database and the application, health check endpoint is available as below:
```
http://localhost:8081/manage/health
```

### Metrics
Some basic metrics capabilities are added to Bring-Your-Own-Interview Spring boot application.

The metric endpoint is http://localhost:8081/manage/metrics. It shows several useful metrics such as JVM memory used, system CPU usage, http requests etc.

**For example:**

Using /manage/metrics/http.server.requests endpoint, we can see the metrics of APIs which are executed with their count, exception, outcome, status, total time etc.

There is a count attribute to know how many times particular endpoint has been called with a particular status.

```
http://localhost:8081/manage/metrics/http.server.requests

Examples:
http://localhost:8081/manage/metrics/http.server.requests?tag=uri:/dashboards&tag=method:GET
http://localhost:8081/manage/metrics/http.server.requests?tag=method:DELETE
http://localhost:8081/manage/metrics/http.server.requests?tag=method:PUT
http://localhost:8081/manage/metrics/http.server.requests?tag=method:POST
```

#### Prometheus
Prometheus is a standalone service which intermittently pulls metrics from the application.
Micrometer application monitoring framework is configured to integrate springboot actuator(manage) metrics with external monitoring system such as Prometheus.
All the application metrics data are made available at an actuator endpoint called /manage/prometheus.The Prometheus server scrapes this endpoint to get metrics data periodically.
Prometheus service runs on port 9090. For example: If you want to check if any API response latency, you can use the query http_server_requests_seconds_max.

Graph representation of the metrics on prometheus service can be accessed using uri

```
http://localhost:9090/graph
```

You can enter a prometheus query expression inside the expression text field and visualize all the metrics for that query.

http_server_requests_seconds_count - is a metric to hold the count of HTTP requests.

http_server_requests_seconds_count{uri="/dashboards"} - returns a metric associated with the request /dashboards.

http_server_requests_seconds_sum - is the sum of the duration of every request your application received at this endpoint.

http_server_requests_seconds_max - gauge metric which gives us the maximum duration of each type of inbound HTTP request.

#### Grafana
TODO

#### Alert manager
TODO

#### Logging - Centralised Logging mechanism
Centralised Logging management can be done by Splunk or ELK

ELK stands for  
E - Elastic Search(no sql database) - store the data
L - LogStash  - process the data
K - Kibana - visualisation purpose UI Layer helps to view the data

How it works?
Spring boot application - Generate a log file -> Logstash will the read data from log file and process it -> elastic search store the data -> kibana will pull the logs from elastic search.

Install Elastic Search using homebrew
https://www.elastic.co/guide/en/elasticsearch/reference/7.15/brew.html
brew services start elastic/tap/elasticsearch-full
run -> http://localhost:9200/

Install LogStash https://www.elastic.co/guide/en/logstash/7.15/installing-logstash.html#brew
brew services start elastic/tap/logstash-full

Install Kibana https://www.elastic.co/guide/en/kibana/7.15/brew.html
-cd to /usr/local/etc/kibana -> update kibna.yml (uncomment the line elasticsearch.hosts: ["http://localhost:9200"])
-brew services start elastic/tap/kibana-full
-http://localhost:5601/app/home

#### Distributed tracing and logging

### CI/CD using Jenkin Pipeline, docker for Spring boot application








