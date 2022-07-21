package com.example.nycschools.Model


import com.google.gson.annotations.SerializedName

data class Schools(
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("dbn")
    val dbn: String? = null,
    @SerializedName("fax_number")
    val faxNumber: String? = null,
    @SerializedName("latitude")
    val latitude: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("longitude")
    val longitude: String? = null,
    @SerializedName("extracurricular_activities")
    val extracurricular_activities: String? = null,
    @SerializedName("overview_paragraph")
    val overviewParagraph: String? = null,
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    @SerializedName("primary_address_line_1")
    val primaryAddressLine1: String? = null,
    @SerializedName("school_email")
    val schoolEmail: String? = null,
    @SerializedName("school_name")
    val schoolName: String? = null,
    @SerializedName("state_code")
    val stateCode: String? = null,
    @SerializedName("total_students")
    val totalStudents: String? = null,
    @SerializedName("website")
    val website: String? = null,
    @SerializedName("zip")
    val zip: String? = null

)