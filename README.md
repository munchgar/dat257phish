# DAT257/DIT257- Agile software project management - Team Phish
UN sustainability project for course in agile management.
The goal selected was [Goal 13](https://www.un.org/sustainabledevelopment/sustainable-development-goals/) Climate Action

# Team members
**Usernames  - Real name**\
[Pontare25](https://github.com/Pontare25)  - Pontus Nellgård  
[Darclander](https://github.com/darclander) - Lukas Carling  
[adrianhak](https://github.com/adrianhak)  - Adrian Håkansson  
[Cladnic](https://github.com/Cladnic)    - Johan Fridlund  
[munchgar](https://github.com/munchgar)   - Jonathan Hedén  
[HersiZa](https://github.com/HersiZa)    - Zakaria Hersi

# Scrum board
For [SCRUMBOARD](https://github.com/munchgar/dat257phish/projects/1) see Projects tab 

# Motivation
This project was created as part of a course (DAT257/DIT257- Agile software project management) taught at Chalmers University in collaboration with the University of Gothenburg. The primary motivateion behind the project was to learn to use Agile (and SCRUM) as a developer framework. 

# Installation
To run the application we recommend using IntelliJ. 
1. Clone the repository
2. Run the application using the Maven tab, scroll down to JavaFX and use the javaFX:run option (In IntelliJ the Maven tab is located at the top right). 

![Alt text](Documents/screenshots/mavenRun.png?raw=true "Maven run")

OBS! This is a Maven project using dependencies from JavaFX. Large portions of the program will run using the regular run button, but some dependencies require the maven support. 

# How to use
First follow the steps in Installation. Once you have run the application using the Maven tab the application will urge you to either sign in or register. 
- Register: Register by entering the prompts.
- Log in: Either log in using the credentials you entered in the register section or use the Demo credentials. Username: Demo, Password: pass. 

# Documents contains our weekly reflections, both the individual and group reflections.
The individual reflections for week X can be located at **Documents\Individual reflections\weekX\***
The group documentation can be found directly in **Documents\** and is one text file, divided by headers. 

# API Reference
- This project was developed using java version 14.01. There have been some issues with using older versions so if there are issues with running we recommend using this version or later. These are set in the pom.xml file and the .iml file.
- Maven [4.0.0](http://maven.apache.org/maven-v4_0_0.xsd)
## Below are a list of the external dependencies handled by Maven (see pom.xml file)
- Maven compiler version 3.8.0
- javafx-maven-plugin version 0.0.4
- For JavaFX we use org.openjfx version 14
- For SQLite Database we use version 3.32.3

# Frameworks used
- GUI: [JavaFX](https://openjfx.io/) (openjfx) 14
- Database: [SQLite](https://www.sqlite.org/index.html), SQLite dependency for [Maven](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc) 
- For managing dependencies [Maven](https://maven.apache.org/) has been used.

# Credits
- Date axis for charts: Christian Schudt and Diego Cirujano
- Setting up JavaFX and Maven: ByteSmyth [Easiest JavaFX Setup, IntelliJ + Maven with Debugger (2020)](https://www.youtube.com/watch?v=4vd-RE0X5Lg&t=535s&ab_channel=ByteSmyth)
- Organising JavaFX windows: Software Development Tutorials [GUI Application Development using JavaFX with Scene Builder](https://www.youtube.com/watch?v=cfGTJVVcWvE&list=PLpFneQZCNR2ktqseX11XRBc5Kyzdg2fbo&ab_channel=SoftwareDevelopmentTutorials)
- UN Sustainability goal 13 [graphics](https://www.un.org/sustainabledevelopment/climate-change) 
