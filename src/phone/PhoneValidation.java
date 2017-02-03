package phone;

import java.io.*;
import java.util.*;
import java.io.BufferedWriter;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;




public class PhoneValidation {

	public static void main(String[] args) throws Exception {
		
		
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
	
		 String s;
		 BufferedWriter bufferedWriter = null;
		 BufferedWriter bw = null;
		 String arr[]=new String[3];
		   FileReader fr=new FileReader("C:\\Users\\RAKMO01\\Desktop\\talend\\PhnStd\\Melissa\\Melissa_data.txt");
		   /*Sample input 
		     ContactId	,Country_ISO_2A	,MD_phone
			003a000002CORE2AAP,GH,23321227216
			003a000002CORVqAAP,BO,59122165900
			003a000002COSoVAAX,ZM,2.61E+11
			003a000002CONfGAAX,US,6263017716
			003a000001qb2VJAAY,US,2538152991
			003a000002COXb8AAH,US,000-000-0000
			003a000001c61ByAAI,US,2538151000
		     
		    */

		   BufferedReader br=new BufferedReader(fr);
		   bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\RAKMO01\\Desktop\\talend\\PhnStd\\Melissa\\Melissa_std.txt"));
		   bw = new BufferedWriter(new FileWriter("C:\\Users\\RAKMO01\\Desktop\\talend\\PhnStd\\Melissa\\Melissa_err.txt"));
		   while ((s=br.readLine())!=null)

			{
			  
			   arr[0]=s.substring(0, 18);//contactID
			   arr[1]=s.substring(19,21);//Country_ISO_2A
			   arr[2]=s.substring(22);//MD_phone
				

			   try {
					  PhoneNumber numberProto = phoneUtil.parse(arr[2], arr[1]);
					  
					  String Std_phn=phoneUtil.format(numberProto, PhoneNumberFormat.INTERNATIONAL);
					  boolean isValid = phoneUtil.isValidNumber(numberProto);
					  PhoneNumberType num_type= phoneUtil.getNumberType(numberProto);
					  
					  PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();
					  String geocoder_loc= geocoder.getDescriptionForNumber(numberProto, Locale.ENGLISH);
					  boolean retval = geocoder_loc.contains(",");
					 
					  PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();
					
					 String carrier=carrierMapper.getNameForNumber(numberProto, Locale.ENGLISH);//carrier name
					 if(retval){//if  geocoder_loc returns output like "Federal Way, WA"

			  
				 bufferedWriter.write(arr[0]+","+arr[1]+","+Std_phn+","+isValid+","+num_type+"," + geocoder_loc+","+ carrier);
			        bufferedWriter.newLine();
			       bufferedWriter.flush();
					 }
					 else//if  geocoder_loc returns output like "Federal Way"
					 {
						 bufferedWriter.write(arr[0]+","+arr[1]+","+Std_phn+","+isValid+","+num_type+"," + geocoder_loc+",,"+ carrier);
						 //Sample output
						 //003a000001qb2VJAAY,US,+1 253-815-2991,true,FIXED_LINE_OR_MOBILE,Federal Way, WA,
					        bufferedWriter.newLine();
					       bufferedWriter.flush();
					 }
			   } catch (NumberParseException e) {
				   		//phone numbers that cant be parsed
					  bw.write(s);
				        bw.newLine();
				       bw.flush();
					}
			   }
		   bufferedWriter.close();br.close();bw.close();
		
		  
		
	}

}
