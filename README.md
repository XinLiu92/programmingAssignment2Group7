# programmingAssignment2Group7

Team members: Kunpeng Xie, Xin Liu

**Required Tool:**

1. Maven3.5.4: To automatic loading up required dependencies, download from:<https://maven.apache.org/download.cgi>
2. Git: Version control, download from <https://git-scm.com/downloads>
3. Intellij IDEA: download from <https://www.jetbrains.com/idea/download/#section=mac>

**Installation:**

pdf verison on git:https://github.com/XinLiu92/programmingAssignment2Group7/blob/master/install.pdf

1. Maven installation guide <https://maven.apache.org/install.html>
2. Git installation:<https://git-scm.com/book/en/v2/Getting-Started-Installing-Git>
3. Intellij installation: <https://www.jetbrains.com/help/idea/install-and-set-up-product.html>

**Add trec car tool to the project:**

1. Navigate to the directory of trec car tool java version which is ~/trec-car-tools-java-master

2. Use maven command to package the trec car tool to jar file and maven will put it in maven local repository mvn clean install *If you got a message like mvn: command not found. Under Linux, you need to set JAVA_HOME and M2_HOME directory in .bash_profile, if there is no such file, just create one. run the following command sudo vi ~/.bash_profile Add the following to the file: //replace the JAVA_HOME and M2_HOME based on your own JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home export JAVA_HOME

   ```
   M2_HOME=/Users/kangkang/Documents/maven/apache-maven-3.3.9
   export M2_HOME
    
   PATH=${PATH}:${JAVA_HOME}/bin:${M2_HOME}/bin
   export PATH
   ```

   save and exit the file and run the following command: source ~/.bash_profile Now you can go the ~/trec-car-tools-java-master to run the command.

3. Clone the programming assignment by <https://github.com/XinLiu92/cs853programmingAssignment1Group7.git> to your local.

4. Open the cloned repository in Intellij, and reimport maven dependencies. Windows type in ctrl+shift+a to find action, type in "reimport", you will find "reimport all maven projects", then select it and press enter. Mac will type in cmd+shift +a instead. All of the necessary dependencies are included in pom.xml

5. Make sure you pass two arguments to the program, the first one is index directory, second one is the data file directory

6. Rebuild the project and run Main.java

7. By changing the boolean variable defualtScore under Main.java to false, you can swich the score function to the one we need to change in assignment spec.

8. Then run the program to get the result.

**Run in command line:**

1. make sure the trec car tool jar file is in .m2 file which is for maven local repository

2. clone **programmingAssignment2Group7** from github(https://github.com/XinLiu92/programmingAssignment2Group7) and cd in to the directory which contains pom.xml and src folder.

3. run

   ```
   mvn compile 
   ```

4. run

   (1) reading queries and write rankings

   for using default score function BM25

   ```
   mvn exec:java -Dexec.mainClass="main.Main" -Dexec.args="true indexPath paragraphFilePath OutlinesFilePath qrelsFilePath"
   ```

   for using custom score function 

   ```
   mvn exec:java -Dexec.mainClass="main.Main" -Dexec.args="true indexPath paragraphFilePath OutlinesFilePath qrelsFilePath"
   ```

   The output files will be created under the repository folder.

   rankResult-defaultScoreFunc.txt/rankResult-customScoreFunc.txt

   (2)Evaluation with trec_eval

   ​    The output file is also under the project repository folder

   (3) Precision atR

   ​	for using default score function BM25

   ```
   mvn exec:java -Dexec.mainClass="main.PrecisionAtR" -Dexec.args="true indexPath paragraphFilePath OutlinesFilePath qrelsFilePath"
   ```

   ​	for using custom score function

   ```
   mvn exec:java -Dexec.mainClass="main.PrecisionAtR" -Dexec.args="false indexPath paragraphFilePath OutlinesFilePath qrelsFilePath"
   ```

   ​	The output files will be created under the repository folder named like:

   ​	precisionAtR-defaultScoreFunc.txt/precisionAtR-customScoreFunc.txt

   (4)MAP

   for using default score function BM25

   ```
   mvn exec:java -Dexec.mainClass="main.MAP" -Dexec.args="true indexPath paragraphFilePath OutlinesFilePath qrelsFilePath"
   ```

   for using custom score function

   ```
   mvn exec:java -Dexec.mainClass="main.MAP" -Dexec.args="false indexPath paragraphFilePath OutlinesFilePath qrelsFilePath"
   ```

   The output files will be created under the repository folder.

   Ap-MAP-defaultScoreFunc.txt/Ap-MAP-customScoreFunc.txt

   (5)nDCG@20

   ​	for using default score function BM25

   ```
   mvn exec:java -Dexec.mainClass="main.NDCG" -Dexec.args="true indexPath paragraphFilePath OutlinesFilePath qrelsFilePath"
   ```

   for using default score function BM25

   ```
   mvn exec:java -Dexec.mainClass="main.NDCG" -Dexec.args="false indexPath paragraphFilePath OutlinesFilePath qrelsFilePath"
   ```

   The output files will be created under the repository folder.

   nDCG-defaultScoreFunc.txt/nDCG-customScoreFunc.txt





   Result:

   https://github.com/XinLiu92/programmingAssignment2Group7/blob/master/result.pdf

   Useful source:

   programming assignment1:https://github.com/XinLiu92/cs853programmingAssignment1Group7
