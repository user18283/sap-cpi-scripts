package com.idocs
import groovy.xml.MarkupBuilder

def idocText = new File("data/groovy/com/idocs/Text_Idoc.txt").text
def lines = idocText.readLines()

def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

xml.IDOC {
    lines.each { line ->
        def parts = line.split("\\|")
        def segmentName = parts[0]

        "$segmentName" {
            parts[1..-1].eachWithIndex { val, i ->
                "FIELD${i + 1}"(val)
            }
        }
    }
}

println writer.toString()
