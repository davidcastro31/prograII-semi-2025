package com.example.miprimeraaplicacion.api.model;

import java.util.List;

public class IncidentResponse {
    private List<Incident> data;
    public List<Incident> getData() { return data; }
    public void setData(List<Incident> data) { this.data = data; }
}
