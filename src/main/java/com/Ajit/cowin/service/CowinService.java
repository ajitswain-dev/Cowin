package com.Ajit.cowin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Ajit.cowin.model.Centers;
import com.Ajit.cowin.model.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CowinService {

	String date="30-05-2021";
	String dist_id="604";
	String url="https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id="+dist_id+"&date="+date;
	//String url="https://restcountries.eu/rest/v2/all";
	
	public List<Centers> getcenter() {
		
		List<Centers> listOfCenter= new ArrayList<Centers>();
		try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            LinkedHashMap<String,String> allCenter=null;
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET,entity,Object.class);
            Object objects = response.getBody();
			
			  if(objects instanceof LinkedHashMap) { 
					
				  allCenter=(LinkedHashMap<String, String>)response.getBody();
					  Object obj=allCenter.get("centers");
					  if(obj instanceof ArrayList) { 
						 ArrayList list=(ArrayList)obj;
						 for(Object o:list)
						 {
							 if(o instanceof LinkedHashMap)
							 {
								 Centers center=new Centers();
								 center.setCenter_id(((LinkedHashMap) o).get("center_id").toString());
								 center.setName(((LinkedHashMap) o).get("name").toString());
								 center.setAddress(((LinkedHashMap) o).get("address").toString());
								 center.setBlock_name(((LinkedHashMap) o).get("block_name").toString());
								 center.setState_name(((LinkedHashMap) o).get("state_name").toString());
								 center.setPincode(((LinkedHashMap) o).get("pincode").toString());
								 center.setLat(((LinkedHashMap) o).get("lat").toString());
								 center.setLongi(((LinkedHashMap) o).get("long").toString());
								 center.setFrom(((LinkedHashMap) o).get("from").toString());
								 center.setTo(((LinkedHashMap) o).get("to").toString());
								 center.setFee_type(((LinkedHashMap) o).get("fee_type").toString());
								 Object sessionlist=((LinkedHashMap) o).get("sessions");
								 List<Session> listOfsession=new ArrayList<Session>();
								 if(sessionlist instanceof ArrayList)
								 {
									 ArrayList sessions=(ArrayList)sessionlist;
									 for(Object l:sessions)
									 {
										 if(l instanceof LinkedHashMap)
										 {
											 Session session=new Session();
											 session.setDate(((LinkedHashMap) l).get("date").toString());
											 session.setAvailable_capacity(((LinkedHashMap) l).get("available_capacity").toString());
											 session.setMin_age_limit(((LinkedHashMap) l).get("min_age_limit").toString());
											 session.setVaccine(((LinkedHashMap) l).get("vaccine").toString());
											 session.setAvailable_capacity_dose1(Integer.parseInt(((LinkedHashMap) l).get("available_capacity_dose1").toString()));
											 session.setAvailable_capacity_dose2(Integer.parseInt(((LinkedHashMap) l).get("available_capacity_dose2").toString()));
											 listOfsession.add(session);
										 }
									 }
								 }
								 //listOfsession.stream().filter(ele->Integer.parseInt(ele.getMin_age_limit())==18).collect(Collectors.toList());
								 //center.setSession(listOfsession);
								 center.setSession(listOfsession.stream().filter(ele->Integer.parseInt(ele.getMin_age_limit())==18 && ele.getAvailable_capacity_dose1()>0).collect(Collectors.toList()));
								 listOfCenter.add(center);
							 }
						 }
					  }
					  //listOfCenter.stream().filter(center->center.getSession().size()>0 && center.get).collect(Collectors.toList());
					 
			  }
			  response.getBody();
			  //ArrayList<LinkedHashMap<Object,Object>>list=center.get("centers");
			  //System.out.println(centers.getClass()); }
			 
            
        } catch (Exception ex) {
           ex.printStackTrace();
	}
		listOfCenter=listOfCenter.stream().filter(center->center.getSession().size()>0).collect(Collectors.toList());
		return  listOfCenter;
	}
}
