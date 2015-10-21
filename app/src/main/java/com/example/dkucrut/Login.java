package com.example.dkucrut;

public class Login extends Koneksi {
    String URL = "http://118.97.87.4/dkucrut/login.php";
    String url = "";
    String response = "";

    public String getBiodataById(int id) {
        try {
            url = URL + "?operasi=get_biodata_by_id&id=" + id;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String insertBiodata(String nama, String alamat) {

        nama = nama.replace(" ", "%20");

        try {
            url = URL + "?operasi=insertBiodata&nama=" + nama + "&alamat=" + alamat;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String insertEvent(String event, String tanggal, String desc) {

        event = event.replace(" ", "`");
        desc = desc.replace(" ", "`");

        try {
            url = URL + "?operasi=insertEvent&event=" + event + "&tanggal=" + tanggal + "&desc=" + desc;
            System.out.println("URL Insert Event : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String insertAkun(String user, String pass) {

        try {
            url = URL + "?operasi=insert&user=" + user + "&pass=" + pass;
            System.out.println("URL Insert Biodata : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

    public String getAkun(String user, String pass) {
        try {
            url = URL + "?operasi=getAkun&user=" + user + "&pass=" + pass;
            System.out.println("URL getAkun : " + url);
            response = call(url);
        } catch (Exception e) {
        }
        return response;
    }

}