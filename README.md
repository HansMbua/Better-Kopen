# Better koper how to run

1. Download and install IntelliJ IDEA: Go to the IntelliJ IDEA website and download the Community Edition or Ultimate Edition based on your requirements. Follow the installation instructions provided on the website.
2. Clone the Spring Boot project: Clone the repository containing the Spring Boot project you want to work on. You can use Git to clone the repository or download it as a ZIP file and extract it.
3. Open the project in IntelliJ IDEA: Open IntelliJ IDEA and select "Open" from the welcome screen. Navigate to the directory where you cloned the project and select the project folder to open it in IntelliJ IDEA.
4. Set up the project SDK: Select the project folder in the Project Explorer window, and then click File > Project Structure. Under Project Settings, select Project, and then set the project SDK to the version of Java that you want to use. If the SDK is not installed, you can click the New button to download and install it.
5. Build the project: Open the Maven tool window by selecting View > Tool Windows > Maven. In the Maven tool window, expand the Lifecycle panel and double-click on the package goal to build the project. This will generate a JAR file in the target directory of your project
6. Run the project: Once the project is built successfully, you can run it from the Run menu. Go to Run > Edit Configurations and create a new configuration for your Spring Boot application. Set the main class to the one containing the `@SpringBootApplication` annotation and the JAR file to the one generated in the previous step. Click OK to save the configuration, and then select it from the dropdown and click Run.
7. Verify the application is running: Once the application is running, open a web browser and navigate to the URL where your application is running. By default, Spring Boot applications run on port 8080, so you can try accessing http://localhost:8080 to see if your application is up and running.

