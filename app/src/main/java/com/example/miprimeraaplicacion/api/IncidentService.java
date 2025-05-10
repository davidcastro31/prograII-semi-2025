package com.example.miprimeraaplicacion.api;

import com.example.miprimeraaplicacion.api.model.IncidentResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IncidentService {
    @GET("incidentes")
    Call<IncidentResponse> getIncidents(
            @Query("minLat") double minLat,
            @Query("maxLat") double maxLat,
            @Query("minLng") double minLng,
            @Query("maxLng") double maxLng
    );
}
