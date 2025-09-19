
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;


public class order extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(order.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/foodorder","root","");  
            String u = request.getParameter("full-name");
            String p = request.getParameter("contact");
            String e = request.getParameter("email");
            String a = request.getParameter("address");
            String food=request.getParameter("food");
            String q = request.getParameter("qa");
            int quantity=Integer.parseInt(q);
            PreparedStatement ps = con.prepareStatement("SELECT `f_price` FROM `foods` WHERE f_name=?");
            ps.setString(1,food);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int price= rs.getInt(1);
            int total =quantity*price;
            PreparedStatement ps1 = con.prepareStatement("INSERT INTO `order`(`Full Name`, `Phone Number`, `Email`, `Address`,`f_name` , `quantity`, `total_amount`) VALUES (?,?,?,?,?,?,?)");
            
            ps1.setString(1, u);
            ps1.setString(2, p);
            ps1.setString(3, e);
            ps1.setString(4, a);
            ps1.setString(5,food);
            ps1.setInt(6,quantity);
            ps1.setInt(7,total);
            ps1.execute();
            out.println("<h1><table align:'center'><tr><th>your order is placed</th></tr></table></h1><br><br>");
            out.println("Item: "+food+"<br>");
            out.println("Price: "+price+"<br>");
            out.println("Quantity: "+quantity+"<br>");
            out.println("----------------------<br>");
            out.println("Total Amount: "+total+"<br><br>");
            out.println("<a href=\"order.html\"><button class=\"btn btn-primary\">Order More</button></a>");
          
          ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(order.class.getName()).log(Level.SEVERE, null, ex); 
        }
    
    } 
}
