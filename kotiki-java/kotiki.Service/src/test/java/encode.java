import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class encode {
    public static void main(String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //aboba
        String rawPass = "code";
        String encoded = encoder.encode(rawPass);
        System.out.println(encoded);
    }
}
