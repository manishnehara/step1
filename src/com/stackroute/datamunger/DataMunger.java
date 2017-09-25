package com.stackroute.datamunger;

import java.util.Arrays;
import java.util.Scanner;

public class DataMunger {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		String query=sc.nextLine();
		DataMunger d= new DataMunger();
		String []split=d.getSplitStrings(query);
//		for(int i=0;i<split.length;i++)
//			System.out.println(split[i]);
		
//		String filename=d.getFile(query);
//		System.out.println();
////		System.out.println("filename : "+filename);
//		String base=d.getBaseQuery(query);
////		System.out.println("Base query : "+base);
//		
//		String filter=d.getConditionsPartQuery(query);
//		System.out.println("Condition part : "+filter);
//		
//		String []conditions=d.getConditions(query);
//		for(int i=0;i<conditions.length;i++)
//			System.out.println("condition "+(i+1)+" : "+conditions[i]);
//		
//		String []operators = {"<", "<=", ">", ">=", "=", "!="};
//		String []cond_split=d.getRelationalOperators(query);
//		
//		
//		System.out.println("Operators");
//		String []oper=d.getLogicalOperators(query);
//		
//		
//		
	}

	/*
	 * we are creating multiple methods, each of them are responsible for extracting
	 * a specific part of the query. However, the problem statement requires us to
	 * print all elements of the parsed queries. Hence, to reduce the complexity, we
	 * are using the parseQuery() method. From inside this method, we are calling
	 * all the methods together, so that we can call this method only from main()
	 * method to print the entire output in console
	 */
	public void parseQuery(String queryString) {
		// call the methods
		getSplitStrings(queryString);
		getFile(queryString);
		getBaseQuery(queryString);
		getConditionsPartQuery(queryString);
		getConditions(queryString);
		getLogicalOperators(queryString);
		getFields(queryString);
		getOrderByFields(queryString);
		getGroupByFields(queryString);
		getAggregateFunctions(queryString);
	}

	/*
	 * this method will split the query string based on space into an array of words
	 * and display it on console
	 */
	public String[] getSplitStrings(String queryString) {
		String []split=queryString.split(" ");
		for(int i=0;i<split.length;i++)
			split[i].toLowerCase();
		return split;
	}

	/*
	 * extract the name of the file from the query. File name can be found after a
	 * space after "from" clause. 
	 * Note:
	 * -----
	 * CSV file can contain a field that contains from as a part of the column name. 
	 * For eg: from_date,from_hrs etc.
	 * 
	 * Please consider this while extracting the file name in this method.
	 */
	public String getFile(String queryString) {
		String file;
		String []split=queryString.split(" ");
		int i;
		for(i=0;i<split.length;i++)
			if(split[i].matches("from"))
				break;
		return split[i+1];
	}

	/*
	 * This method is used to extract the baseQuery from the query string. BaseQuery
	 * contains from the beginning of the query till the where clause
	 * 
	 * Note:
	 * ------- 
	 * 1. the query might not contain where clause but contain order by or
	 *    group by clause 
	 * 2. the query might not contain where, order by or group by clause 
	 * 3. the query might not contain where, but can contain both group by
	 *    and order by clause
	 */
	public String getBaseQuery(String queryString) {

		String []base=queryString.split(" where");
		return base[0];

	}

	/*
	 * This method is used to extract the conditions part from the query string. The
	 * conditions part contains starting from where keyword till the next keyword,
	 * which is either group by or order by clause. In case of absence of both group
	 * by and order by clause, it will contain till the end of the query string.
	 * Note: 
	 * ----- 
	 * 1. The field name or value in the condition can contain keywords
	 * as a substring. 
	 * For eg: from_city,job_order_no,group_no etc. 
	 * 2. The query might not contain where clause at all.
	 */
	public String getConditionsPartQuery(String queryString) {

		String []condition=queryString.split("where ");
		String []result;
		result=condition[1].split("group by | order by");
		return result[0];
		

	}

	/*
	 * This method will extract condition(s) from the query string. The query can
	 * contain one or multiple conditions. In case of multiple conditions, the
	 * conditions will be separated by AND/OR keywords. 
	 * for eg: 
	 * Input: select city,winner,player_match from ipl.csv where season > 2014 and city
	 * ='Bangalore'
	 * 
	 * This method will return a string array ["season > 2014","city ='Bangalore'"]
	 * and print the array
	 * 
	 * Note: 
	 * ----- 
	 * 1. The field name or value in the condition can contain keywords
	 * as a substring. 
	 * For eg: from_city,job_order_no,group_no etc. 
	 * 2. The query might not contain where clause at all.
	 */
	public String[] getConditions(String queryString) {
		String condition=getConditionsPartQuery(queryString);
		String []split_cond= condition.split(" and | or ");
		return split_cond;
		
	}

	
	public String[] getRelationalOperators(String queryString)
	{
		String condition=getConditionsPartQuery(queryString);
		String []operators=condition.split(" and | or ");
		
		return operators;
		
	}
	/*
	 * This method will extract logical operators(AND/OR) from the query string. The
	 * extracted logical operators will be stored in a String array which will be
	 * returned by the method and the same will be printed 
	 * Note: 
	 * ------- 
	 * 1. AND/OR keyword will exist in the query only if where conditions exists and it
	 * contains multiple conditions. 
	 * 2. AND/OR can exist as a substring in the conditions as well. 
	 * For eg: name='Alexander',color='Red' etc. 
	 * Please consider these as well when extracting the logical operators.
	 * 
	 */
	public String[] getLogicalOperators(String queryString) {
		String []split=queryString.split(" ");
		int c=0;
		for(int i=0;i<split.length;i++)
		{	if(split[i].equals("and") || split[i].equals("or") || split[i].equals("not"))
			{
				System.out.println("Operators "+(++c)+" : "+split[i]);
				
			}
		}
		return split;
		
	}
		

	/*
	 * This method will extract the fields to be selected from the query string. The
	 * query string can have multiple fields separated by comma. The extracted
	 * fields will be stored in a String array which is to be printed in console as
	 * well as to be returned by the method
	 * 
	 * Note: 
	 * ------ 
	 * 1. The field name or value in the condition can contain keywords
	 * as a substring. 
	 * For eg: from_city,job_order_no,group_no etc. 
	 * 2. The field name can contain '*'
	 * 
	 */
	public String[] getFields(String queryString) {
		String []split=queryString.split("from");
		String []field=queryString.split(",");
		for(int i=0;i<field.length;i++)
			System.out.println(field[i]);
				return field;

	}

	/*
	 * This method extracts the order by fields from the query string. 
	 * Note: 
	 * ------
	 * 1. The query string can contain more than one order by fields. 
	 * 2. The query string might not contain order by clause at all. 
	 * 3. The field names,condition values might contain "order" as a substring. 
	 * For eg:order_number,job_order 
	 * Consider this while extracting the order by fields
	 */
	public String[] getOrderByFields(String queryString) {

		return null;
	}

	/*
	 * This method extracts the group by fields from the query string. 
	 * Note: 
	 * ------
	 * 1. The query string can contain more than one group by fields. 
	 * 2. The query string might not contain group by clause at all. 
	 * 3. The field names,condition values might contain "group" as a substring. 
	 * For eg: newsgroup_name
	 * 
	 * Consider this while extracting the group by fields
	 */
	public String[] getGroupByFields(String queryString) {

		return null;
	}

	/*
	 * This method extracts the aggregate functions from the query string. 
	 * Note:
	 * ------ 
	 * 1. aggregate functions will start with "sum"/"count"/"min"/"max"/"avg"
	 * followed by "(" 
	 * 2. The field names might contain"sum"/"count"/"min"/"max"/"avg" as a substring.
	 * For eg: account_number,consumed_qty,nominee_name
	 * 
	 * Consider this while extracting the aggregate functions
	 */
	public String[] getAggregateFunctions(String queryString) {

		return null;
	}

}