package com.beefirst.sns.model

data class IncidentModel(
    val baseURL: String,
    val incidents: List<Incident>
)

data class Incident(
    val assigneeId: Any,
    val createdAt: String,
    val description: String,
    val id: String,
    val issuerId: String,
    val latitude: Double,
    val longitude: Double,
    val medias: List<Media>,
    val priority: Any,
    val status: Int,
    val typeId: Int,
    val updatedAt: String
)

data class Media(
    val id: String,
    val incidentId: String,
    val mimeType: String,
    val type: Int,
    val url: String
)