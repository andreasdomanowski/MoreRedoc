[![Build Status](https://travis-ci.com/andauh/MoreRedoc.svg?branch=master)](https://travis-ci.com/andauh/MoreRedoc)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

# April 28, 2021 - Important Notice!
As of now, [JBoss DNA Common](https://mvnrepository.com/artifact/org.jboss.dna/dna-common/0.7) is hosted on just the Spring Plugins repository. Since the end of 2020, the Spring artifactory does not permit the anonymous download of 3rd party software (see [here](https://spring.io/blog/2020/10/29/notice-of-permissions-changes-to-repo-spring-io-fall-and-winter-2020)). Therefore, MoreRedoc can't be built and is out of order for now until I find the time to deal with this issue.

# MoreRedoc
**Mo**deling of **Re**documented **Re**quirement **Doc**uments

MoreRedoc takes redocumented requirements and tries to generate UML class diagrams according to these requirements.
For now, redocumentation generated with SoftRedoc (written by Harry Sneed) is supported. 

In general, this tool takes two csv files as input:
* one containing the full text for each requirement, separated for each requirement
* one with keywords from the requirements, which are candidates for being domain concepts

SoftRedoc provides these two inputs after redocumenting requirement documents. [Other tools or approaches](#support-for-other-tools) can easily be added, too. 

This tool was developed for my minor thesis (*Towards an Automatic Generation of UML Models from Redocumented Textual Requirements*, supervision: Dr. Birgit Demuth, Professur für Softwartechnologie, TU Dresden). It  includes a detailed explanation of the approach and literature review of other approaches and will be available on GitHub, too.

## Prerequisites
- JDK 8
- Maven 3
- at least 8 GiB of RAM
    - use JVM option `-Xmx8G`
    
MoreRedoc was developed and tested on Windows 10, AdoptOpenJDK 1.8.0_252 & Maven 3.6.3.

## Building and Usage
### With Maven
1. Download or clone this repo
2. Build it either as a
    1. Fat jar containing all dependencies for portability purposes
         `mvn clean compile assembly:assembly`
    2. Regular jar via `mvn clean package`
3. Run the compiled jar with `java -jar -Xmx8G {name of the generated jar}.jar`

### With your IDE
For Integrating it in your IDE, just import the maven project. Increase the JVM's heap space in your run configuration.
* `moreredoc.application.MoreRedocGuiStarter` starts the GUI
* `moreredoc.application.MoreRedocStarter` provides an API

### Usage
1. Select the respective CSVs and modeling options
2. Generate the model
    * Output can be found in the specified folder
    * Model's filenames include a timestamp when they were generated

Remember to increase the JVM's heap size in your run configuration.

## License
- GPLv3, see [LICENSE](LICENSE)

## Support for other tools
Including other (re)documentation tools can easily be achieved.
- Implement a `moreredoc.datainput.InputDataHandler`for your tool
- Add the tool with a reference to your *InputDataHandler* to the enum of supported tools in `moreredoc.datainput.tools.SupportedRedocumentationTools`
