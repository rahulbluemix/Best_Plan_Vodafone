package util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TypeUsageCDR {
	
	Connection con=null;
	ArrayList<String> bestPlanValue = new ArrayList<String>();
	
	public ArrayList<String> newMain() {
		
		BufferedReader br = null;
		int i =0;
		int var=0;
		int count=0;
		int final_count =0;
		String max_usagetype = null;
		int totalconsumedUnit = 0;
		JOptionPane frame=null;
		String subscriber_no =null;
		ResultSet rs = null;
		Float fixed_charge_amt ;
		int add_fixed_amt = 0;
		int add_unit_rate=0;
		int current_total_consumption = 0;
		int proposed_total_consumption = 0;
		String description_text = null;
		String description_element_txt = null;
		int new_proposed_con = 0;
		int proposed_elm_id  = 0;

		try{
			
				Class.forName("com.mysql.jdbc.Driver");
				con=(Connection)DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-04.cleardb.net:3306/ad_977c14809b1f973", "b132dd1266c62b", "d8d5af3d");
				if (con!=null)
				{
				//	System.out.println("Connection established successfully");
				}
			}
		catch(Exception e){
				System.out.println("Error Not connected successfully" + e);
			}
		
		try {
				
				String sCurrentLine[] = new String[100];
				String typeId[] = new String[100];
				String ConsumedUnit[] = new String[100];
				String[] new_array= new String[100];
				int k=0;
				br = new BufferedReader(new FileReader("C:\\SampleCDR.txt"));
				
				while ((sCurrentLine[i] = br.readLine()) != null){
					i++;
				}
				for(int j =1;j<i-1;j++)
				{	
						typeId[k]=sCurrentLine[j].substring(3,8);
						ConsumedUnit[k] = sCurrentLine[j].substring(23,33).trim();
						subscriber_no = sCurrentLine[j].substring(39,52).trim();
						k++;
				}
				
				while(typeId[var] !=null)
				{
					var++;
				}
				
				for(int x=0; x < var; x++){ 
			          for(int j=x; j < var; j++){ 
			        	  if(typeId[x].equals(typeId[j])){ 
			        		  count++;
			        	  } 
			        	  if (final_count < count)
			        	  {
				        	  final_count = count;
				        	  new_array[0] =typeId[x];
				        	  totalconsumedUnit += Integer.parseInt(ConsumedUnit[j]);
			        	  }
			          }
			          count=0;	     
				}
	
				max_usagetype = new_array[0];
	
			}catch (IOException e) {
			e.printStackTrace();
		}
		try{
			Statement st = con.createStatement();
			String sql = "select billing_units_type,description_code from usage_type where type_id_usage=" + max_usagetype;
			rs = st.executeQuery(sql);
			if(!rs.isBeforeFirst())
			{
				JOptionPane.showMessageDialog(frame, "No records Found");
			}
			
			while(rs.next())
			{ 
				int billing_units_type = rs.getInt("billing_units_type");  //Billing units
				
				
				String description = "select description_text from description where description_code =" + rs.getInt("description_code");
				rs = st.executeQuery(description);
				while(rs.next())
				{ 
					description_text = rs.getString("description_text"); // Description of usage type
					
					String description_elemnt = "select description_text from description where description_code=" +
					"(select description_code from product_elements where element_id = (select element_id from usage_type where type_id_usage = " +  max_usagetype + "))";
					rs = st.executeQuery(description_elemnt);
					
					while(rs.next())
					{
						description_element_txt = rs.getString("description_text"); // Description of Element Id

						rs= st.executeQuery("select * from UNITS_TYPE_REF where units_type=" + billing_units_type );
						
						while(rs.next())
						{
							rs= st.executeQuery("select add_fixed_amt,fixed_charge_amt, add_unit_rate,element_id from RATE_USAGE " +
									"where element_id = (select element_id from product where subscr_no=" + subscriber_no + ")");
							
							while(rs.next())
							{
								fixed_charge_amt = rs.getFloat("fixed_charge_amt");
								add_fixed_amt = rs.getInt("add_fixed_amt");
								add_unit_rate = rs.getInt("add_unit_rate");
								current_total_consumption = Math.round(fixed_charge_amt) + add_fixed_amt + add_unit_rate*totalconsumedUnit;
								
								
								/*JOptionPane.showMessageDialog(frame, "Subscriber Number: " + subscriber_no + "\n" +
								"Frequent Call Type: " + description_text + "\n" + 
								"Total units Consumed: " + totalconsumedUnit + "Seconds" + "\n" +
								"Current Price Plan: " + description_element_txt + "\n" +
								"Bill amount of" + description_text + " on current price plan: " + current_total_consumption + " €"); 
								*/
								//bestPlanValue.add("\nSubscriber Number:  "  + subscriber_no);
								//bestPlanValue.add("\nFrequent Call Type: "  + description_text);
								//bestPlanValue.add("\nTotal units Consumed: "  + totalconsumedUnit);
								//bestPlanValue.add("\nCurrent Price Plan: "  + description_element_txt);
								//bestPlanValue.add("\nBill amount of " + description_text + " on current price plan: " + current_total_consumption);
								bestPlanValue.add(subscriber_no);
								bestPlanValue.add(description_text);
								bestPlanValue.add(totalconsumedUnit + " Seconds");
								bestPlanValue.add(description_element_txt);
								bestPlanValue.add(current_total_consumption + " Euro");
								
								
							}
						}
					}
				}
			}
			
			String best_rate_usage = "select * from rate_usage where type_id_usg = " + max_usagetype;
			rs= st.executeQuery(best_rate_usage);
			while (rs.next())
			{
				fixed_charge_amt = rs.getFloat("fixed_charge_amt");
				add_fixed_amt = rs.getInt("add_fixed_amt");
				add_unit_rate = rs.getInt("add_unit_rate");
				proposed_total_consumption = Math.round(fixed_charge_amt) + add_fixed_amt + add_unit_rate*totalconsumedUnit;
				if (proposed_total_consumption < current_total_consumption)
				{
					if (new_proposed_con < proposed_total_consumption)
					{
						new_proposed_con = proposed_total_consumption;
						proposed_elm_id = rs.getInt("element_id");
					}
				}
			}
			
			String best_plan = "select * from description where description_code = " +
					"(select description_code from ad_977c14809b1f973.product_elements where element_id = " + proposed_elm_id + ")";
			rs= st.executeQuery(best_plan);
			while (rs.next())
			{
				/*JOptionPane.showMessageDialog(frame, "Subscriber Number: " + subscriber_no + "\n" +
						"Frequent Call Type: " + description_text + "\n" + 
						"Total units Consumed: " + totalconsumedUnit + "Seconds" + "\n" +
						"Proposed Price Plan: " + rs.getString("description_text")+ "\n" +
						"Bill amount of" + description_text + " on Proposed price plan: " + new_proposed_con + " €");*/
				
				//bestPlanValue.add("\nSubscriber Number:  "  + subscriber_no);
				//bestPlanValue.add("\nFrequent Call Type: "  + description_text);
				//bestPlanValue.add("\nTotal units Consumed: "  + totalconsumedUnit);
				//bestPlanValue.add("\nProposed Price Plan: "  + rs.getString("description_text"));
				//bestPlanValue.add("\nBill amount of " + description_text + " on Proposed price plan: " + new_proposed_con +" €");
				bestPlanValue.add(rs.getString("description_text"));
				bestPlanValue.add(new_proposed_con +" Euro");
			
			
			}
			
			st.close();
			con.close();
			 
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	return bestPlanValue;
	}
	

}
