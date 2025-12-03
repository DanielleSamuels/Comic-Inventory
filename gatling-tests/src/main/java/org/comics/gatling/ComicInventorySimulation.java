package org.comics.gatling;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ComicInventorySimulation extends Simulation {
    private static final String GATEWAY_BASE_URL = "http://localhost:8080";

    private static final String BEARER_TOKEN =
            "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI5bTRBUXM1MXc2cGpuOExjSk1WZmFoYnVSOElKWDE2dVFVLUJ0Uk5PTmNnIn0.eyJleHAiOjE3NjM0MzQ4ODEsImlhdCI6MTc2MzQzNDU4MSwianRpIjoiM2I1Nzc4YTQtMzRiMS00ZDU3LThkMGItNTU1ZjFkMjk0YjRmIiwiaXNzIjoiaHR0cDovL2tleWNsb2FrOjgwODUvcmVhbG1zL2NvbWljLWludmVudG9yeSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI5M2I3ZjNlNC01M2E4LTQwODgtOTIwMi1iODllYWY3ZTJhYjciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjb21pY3MiLCJzaWQiOiJlMzE3ZmNmZS02YzY4LTRiZTMtODYzOC01NGM4ZTcwMjJmM2YiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1jb21pYy1pbnZlbnRvcnkiLCJvZmZsaW5lX2FjY2VzcyIsIkFETUlOIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJjb21pY3MiOnsicm9sZXMiOlsiY29taWMtYWRtaW4iXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1jb21pYy1pbnZlbnRvcnkiLCJvZmZsaW5lX2FjY2VzcyIsIkFETUlOIiwidW1hX2F1dGhvcml6YXRpb24iXSwibmFtZSI6IlRlc3QgQWRtaW4iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0YWRtaW5AZXhhbXBsZS5jb20iLCJnaXZlbl9uYW1lIjoiVGVzdCIsImZhbWlseV9uYW1lIjoiQWRtaW4iLCJlbWFpbCI6InRlc3RhZG1pbkBleGFtcGxlLmNvbSJ9.OGqfoYf6b_0e4jz9d0P0siwnS1e0vNeIIixtkxYNtw3kkJw2EVPDYqif85r-8UTkc4BK0hosj2sYsB1DNl7kkTAgSR5EAvXfcVgjnt9OEOIX-0fUMz_YT3QQ4UqIUO1bqkvg2dnI6WEFPDk0vCISkhnKB4JtJGfW2MZT4i-oCxVbnqIo3lOgpNUV-Gx-n88c3GdzPNHJCUGTWzkhrglskvyQPRCXABVAfw9P7jN17DKO6aWdbpt30ERdv4NgWTZqt0a-dNhALfUWuKYCpqMQ4QzHgD35C-b7IdzzQUP9lp7KybTSsqiKv8shcpMA1ZkerIS0Vu1xVFjG0KIi5qWN2A";
    HttpProtocolBuilder httpProtocol = http
        .baseUrl(GATEWAY_BASE_URL)
        .acceptHeader("application/json");

    ScenarioBuilder scn = scenario("Comic-Inventory Load")
        .exec(
            http("Get comic inventory")
                .get("/library/v1/comics/1/items")
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .check(status().is(200))
        );

    {
        setUp(
            scn.injectOpen(
                rampUsers(50).during(30),
                constantUsersPerSec(50).during(30)
            )
        ).protocols(httpProtocol);
    }
}