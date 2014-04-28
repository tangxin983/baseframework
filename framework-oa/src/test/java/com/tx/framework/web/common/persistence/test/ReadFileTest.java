package com.tx.framework.web.common.persistence.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class ReadFileTest {

	@Test
    public void readFile01() throws IOException {
        FileReader fr=new FileReader("E:/icon.txt");
        BufferedReader br=new BufferedReader(fr);
        String line="";
        while ((line=br.readLine())!=null) {
            System.out.println("<option value=\""+ line.substring(20) + "\" data-content=\"<span class='" +  line + "'></span> " + line.substring(20) + "\">"+ line.substring(20) + "</option>");
        }
        br.close();
        fr.close();
    }
}
