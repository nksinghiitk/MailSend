# MailSend
1. Download all the files and put in one folder
2. Rename file configMail.properties.test to configMail.properties
3. Open configMail.properties and change all values 
    - smtpadd=smtp.gmail.com
    - smtpprt=465 or 587
    - fromadd=nksinghiitk@gmail.com
    - passwd=xxxxxxxxxxx
4. Open courseconfig.properties and change all the values
   - subject=Your Blab test grade Card 
   - headerm=Email,Midsem,Endsem
   - filename=markslist.txt
   - instructormail=singhnk@iitk.ac.in
5. Add yours marks list file to folder and change the name in courseconfig.properties file.
6. Finally run the command sh sm.sh
