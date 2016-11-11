#!/usr/bin/env ruby

#Created by Ioannis Gabrielides

#This script will use the files inside the parser_tests folder and generate
# methods for WACCVisitorTest.java


require 'date'
@test_folder = '../test_files/'
@filename = 'WACCVisitorTest.java'

#Generate functions

def gen_tests(file, name, return_value)
  "
  @Test
  public void test#{name}() {
    try {
        FileInputStream in = new FileInputStream(new File
                (\"src/test/test_files/#{file}\"));
        assertThat(Compile.compileInputStream(in), is(#{return_value}));
    } catch (IOException e) {
        e.printStackTrace();
    }
  }"
end

$functions = ''


Dir.glob("#{@test_folder}invalidSyntax/**/*") do |item|
  #Iterate through every item in the directory.
  if item.include? '.wacc'
    test_name = item.slice((item.rindex('/')+1)..(item.length - 1 - '.wacc'.length))
    next if test_name.include? '-' or test_name.include? '~' or test_name.include? '.' or test_name.include? '/'
    $functions = "#{$functions}\n#{gen_tests(item, test_name, 100)}"
  end
end

Dir.glob("#{@test_folder}invalidSemantic/**/*") do |item|
  #Iterate through every item in the directory.
  if item.include? '.wacc'
    test_name = item.slice((item.rindex('/')+1)..(item.length - 1 - '.wacc'.length))
    next if test_name.include? '-' or test_name.include? '~' or test_name.include? '.' or test_name.include? '/'
    $functions = "#{$functions}\n#{gen_tests(item, test_name, 200)}"
  end
end

Dir.glob("#{@test_folder}validSemantic/**/*") do |item|
  #Iterate through every item in the directory.
  if item.include? '.wacc'
    test_name = item.slice((item.rindex('/')+1)..(item.length - 1 - '.wacc'.length))
    next if test_name.include? '-' or test_name.include? '~' or test_name.include? '.' or test_name.include? '/'
    $functions = "#{$functions}\n#{gen_tests(item, test_name, 0)}"
  end
end

#Write functions to file
File.open(@filename, 'w') {|f|
  f.write("
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

//Ruby generated test suite. To check WACCVisitor.
//Created at: #{Time.now.strftime('%d/%m/%Y %H:%M')}.

public class WACCVisitorTest {
  #{$functions}
}
")}
