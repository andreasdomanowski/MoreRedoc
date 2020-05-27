[![Build Status](https://travis-ci.com/andauh/MoreRedoc.svg?branch=master)](https://travis-ci.com/andauh/MoreRedoc)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

# MoreRedoc
**Mo**deling of **Re**documented **Re**quirement **Doc**uments

MoreRedoc takes redocumented requirements and tries to generate UML class diagrams according to these requirements. For now, SoftRedoc (written by Harry Sneed) is supported. 

Necessary input for modeling is a CSV with the redocumented keywords from the requirements and a CSV with the corresponding full text of the requirements.

This software was developed for my minor thesis (*Towards an Automatic Generation of UML Models from Redocumented Textual Requirements*). The thesis will be available on GitHub, too. It will include a detailled explanation of the approach and literature review. The thesis is supervised by Dr. Birgit Demuth, Professur für Softwartechnologie, TU Dresden.

## Prerequisites
- JDK 1.8
- Maven 3
- at least 8 GiB of RAM
    - VM Option *-Xmx8G*

## Build and run
### Via maven
1. Download or clone this repo
2. Build the project via
`mvn clean compile assembly:single`
3. Run the compiled jar with 
`java -jar -Xmx8G {name of the generated jar}.jar`
4. Select the respective CSVs and modeling options
5. Generate the model. Output can be found in the specified folder

### Via IDE
For Integrating it in your IDE, just import the maven project.
* `moreredoc.application.MoreRedocGuiStarter` starts the GUI
* `moreredoc.application.MoreRedocStarter` provides an API for the modeling

## License
- GPLv3, see [LICENSE](LICENSE)

## Support for other (Re)documentation tools
- Implement a `moreredoc.datainput.InputDataHandler`for your tool
- Add the tool with a reference to your *InputDataHandler* to the enum of supported tools in `moreredoc.datainput.tools.SupportedRedocumentationTools`
