import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
        private String user;// 用户名
        private String password;// 密码
        private String age;
        private String sex;
        private String telephone;
        private String address;
        public User(){}

        public User(String user, String password, String age,String sex, String telephone, String address){
            this.age=age;
            this.sex=sex;
            this.telephone=telephone;
            this.address=address;
            this.user=user;
            this.password=password;
        }

        public void setAddress(String address){ this.address=address;}
        public String getAddress(){ return address;}
        public void setTelephone(String telephone){ this.telephone=telephone;}
        public String getTelephone(){ return telephone;}
        public String getUser(){
            return user;
        }
        public String getPassword(){
            return password;
        }
        public String getSex(){return  sex;}
        public String getAge(){return age;}
        public void setAge(String  age){ this.age=age;}
        public void setSex(String sex){ this.sex=sex;}
        public void setUser(String user){
            this.user=user;
        }
        public void setPassword(String password){
            this.password=password;
        }
    }


