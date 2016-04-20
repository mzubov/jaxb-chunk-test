package main

import java.io.FileWriter
import javax.xml.bind.{JAXBElement, Marshaller, JAXBContext}
import javax.xml.namespace.QName
import javax.xml.stream.XMLOutputFactory

import generated.Licenses.License
import generated.ObjectFactory

/**
  * Created by mz on 20.04.16.
  */
object Main {
  def main(args: Array[String]): Unit = {
    val runtime = Runtime.getRuntime
    val context = JAXBContext.newInstance("generated")
    val writer = new FileWriter("output.xml")
    val xmlWriter = XMLOutputFactory.newFactory().createXMLStreamWriter(writer)
    val factory = new ObjectFactory
    val marshaller = context.createMarshaller()
    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true)
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)

    val qname = new QName("", "license")
    xmlWriter.writeStartDocument()

    println(s"Free memory: ${runtime.freeMemory()}")
    for(x <- 1 to 500 * 1000){
      val license = factory.createLicensesLicense()
      license.setSysGuid("123321123321")
      license.setSchoolGuid("aaaaaa55555")
      license.setStatusName("NOT_VALID")
      license.setSchoolName("Детский сад")
      license.setShortName("дс")
      license.setSchoolTypeName("asda")
      license.setLawAddress("city town")
      license.setOrgName("ron")
      license.setRegNum("123")
      license.setDateLicDoc("2016-01-01")//wat?!
      license.setDateEnd("2017-01-01")//wat?!

      val attachments = factory.createLicensesLicenseSupplements()
      val attach1 = factory.createLicensesLicenseSupplementsSupplement()
      attach1.setDateLicDoc("2016-02-02")
      attach1.setLawAddress("town city")
      attach1.setLicenseFK(license.getSysGuid)
      attach1.setNumber("1")
      attach1.setNumLicDoc("123")
      attach1.setOrgName("asdasdasd")
      attach1.setSchoolGuid("aaaaaa555")
      attach1.setSchoolName("5")
      attach1.setShortName("2")
      attach1.setStatusName("asd")
      attach1.setSysGuid("45645645654")

      val programs = factory.createLicensesLicenseSupplementsSupplementLicensedPrograms()
      val program1 = factory.createLicensesLicenseSupplementsSupplementLicensedProgramsLicensedProgram()
      program1.setCode("11.11.11")
      program1.setEduLevelName("asd")
      program1.setEduProgramKind("qwe")
      program1.setEduProgramType("fgh")
      program1.setName("1qaz")
      program1.setQualificationCode("00.00.00")
      program1.setQualificationName("tyu")
      program1.setSupplementFk(attach1.getSysGuid)
      program1.setSysGuid("123asd123asd")

      programs.getContent.add(factory.createLicensesLicenseSupplementsSupplementLicensedProgramsLicensedProgram(program1))
      attach1.setLicensedPrograms(programs)

      val attach2 = factory.createLicensesLicenseSupplementsSupplement()
      attach2.setDateLicDoc("2016-02-02")
      attach2.setLawAddress("town city")
      attach2.setLicenseFK(license.getSysGuid)
      attach2.setNumber("2")
      attach2.setNumLicDoc("123")
      attach2.setOrgName("asdasdasd")
      attach2.setSchoolGuid("aaaaaa555")
      attach2.setSchoolName("5")
      attach2.setShortName("2")
      attach2.setStatusName("asd")
      attach2.setSysGuid("97650986598754")

      attachments.getContent.add(factory.createLicensesLicenseSupplementsSupplement(attach1))
      attachments.getContent.add(factory.createLicensesLicenseSupplementsSupplement(attach2))

      license.setSupplements(attachments)

      val licenseElement = new JAXBElement[License](qname, classOf[License], license)
      marshaller.marshal(licenseElement, writer)

      if (x % 1000 == 0)
        println(s"Free memory: ${runtime.freeMemory()}")
    }
    xmlWriter.writeEndDocument()
    xmlWriter.close()
    writer.close()
  }
}
