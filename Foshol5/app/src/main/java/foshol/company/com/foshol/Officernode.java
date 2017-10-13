package foshol.company.com.foshol;

/**
 * Created by User on 21-Aug-17.
 */

class Officernode {
    String naam,jela,shohor,mail,pass,stat;
    Officernode(){

    }
    Officernode(String n,String m,String p,String j,String s,String stat){
        naam=n;
        jela=j;
        shohor=s;
        mail=m;
        pass=p;
        this.stat=stat;

    }

    public String getStat() {
        return stat;
    }

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
}
