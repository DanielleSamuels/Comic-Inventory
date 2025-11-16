package org.comics.gatling;

import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ComicInventorySimulation extends Simulation {
    private static final String GATEWAY_BASE_URL = "http://localhost:8080";

    private static final String BEARER_TOKEN =
            "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJpdmtkSzRHbGlLQXNzejBLTDhBU1VOcWgyOGdsTW9GVm5XeVM4ZE4xTEw4In0.eyJleHAiOjE3NjMyNzk3OTEsImlhdCI6MTc2MzI3OTQ5MSwianRpIjoiODI4MWMwYzYtNzI2ZC00OTJjLTkxNTAtODY3ZmQ3YWZmYmQ0IiwiaXNzIjoiaHR0cDovL2tleWNsb2FrOjgwODUvcmVhbG1zL2NvbWljLWludmVudG9yeSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJiODcwZWY3Zi03NTE3LTRjYzMtYmJkZC0yMDg2ZjhlNTllMzciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjb21pY3MiLCJzaWQiOiJiZjg1NWI3Ny1hMGMwLTRjZTItYjZiZC1iZGIxYTQzNTk1ODYiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1jb21pYy1pbnZlbnRvcnkiLCJvZmZsaW5lX2FjY2VzcyIsIkFETUlOIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJjb21pY3MiOnsicm9sZXMiOlsiY29taWMtYWRtaW4iXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIHByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1jb21pYy1pbnZlbnRvcnkiLCJvZmZsaW5lX2FjY2VzcyIsIkFETUlOIiwidW1hX2F1dGhvcml6YXRpb24iXSwibmFtZSI6IlRlc3QgQWRtaW4iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0YWRtaW5AZXhhbXBsZS5jb20iLCJnaXZlbl9uYW1lIjoiVGVzdCIsImZhbWlseV9uYW1lIjoiQWRtaW4iLCJlbWFpbCI6InRlc3RhZG1pbkBleGFtcGxlLmNvbSJ9.RhwFMenBn1TLZ9kVfbwXxv8xm6IAHeE-VOBT_wt-5le5PUNHgg1KW4S0XkMV7x45TJIFn3sH1PTQpan-5dy46jLsuYqfOZlUfuwxgTIuoOcRbseH4qd0H3IIfMC8LRLjSjw5WUr51HrB7iDPu_bLdqWO2LcuR2REs6fkZv_sGoZsYOvOrsvf-mh8yWdNu5nHnubwSpPzBEmU18vn84tn5EKW8WGOb6vhFVLOob4WBW9DpvWpoJeNOOwM_TDyq5taEZtVavD3tLjqFJfcsDDnKbegnuT2LyFu344z0PbIVmtP-6fqbwP65gAWQNWwPWA6rqSEmIBfcbkesfYm31QuxA";
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