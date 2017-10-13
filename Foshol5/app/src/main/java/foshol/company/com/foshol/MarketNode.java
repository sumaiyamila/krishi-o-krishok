package foshol.company.com.foshol;

/**
 * Created by User on 8/31/2017.
 */

public class MarketNode {
    private String id;
     private String naam;
    private String jela;
    private String shohor;
    private String mail;
    private String pass;
    private String shopname;
    private String stat;
     public MarketNode(){

        }

    public MarketNode(String n, String m, String p, String j, String s, String sh, String stat, String id){
       // this.Id=Id;
        naam=n;
        jela=j;
        shohor=s;
        mail=m;
        pass=p;
        shopname=sh;
        this.stat=stat;
        this.id=id;
    }

    public  String getId() {return id;}



    public String getNaam() {
            return naam;
        }

        public String getJela() {
            return jela;
        }

        public String getShohor() {
            return shohor;
        }

        public String getMail() {
            return mail;
        }

        public String getPass() {
            return pass;
        }

        public String getShopname() {
            return shopname;
        }


        public String getStat() {
            return stat;
        }


    }

