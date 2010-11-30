/* Copyright 2006-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package grails.plugin.databasemigration.dbdoc

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class HTMLListWriter {

	private String directory
	private String filename
	private String title
	private Map files

	HTMLListWriter(String title, String filename, String subdir, Map files) {
		this.title = title
		this.filename = filename
		this.directory = subdir
		this.files = files
	}

	void writeHTML(SortedSet objects) {
		StringBuilder content = new StringBuilder()
		content.append """\
<HTML>
<HEAD>
<TITLE>
$title
</TITLE>
<LINK REL ="stylesheet" TYPE="text/css" HREF="stylesheet" TITLE="Style">
</HEAD>
</HEAD>
<BODY BGCOLOR="white">
<FONT size="+1" CLASS="FrameHeadingFont">
<B>$title</B></FONT>
<BR>
<TABLE BORDER="0" WIDTH="100%" SUMMARY=""><TR>
<TD NOWRAP><FONT CLASS="FrameItemFont">
"""

		for (object in objects) {
			String s = object.toString()
			String hrefName = s.toLowerCase().endsWith('.xml') ? s[0..-5] : s
			content.append """<A HREF="$directory/${hrefName.toLowerCase()}" target="objectFrame">"""
			content.append s
			content.append "</A><BR>\n"
		}

		content.append '''\
</FONT></TD>
</TR>
</TABLE>

</BODY>
</HTML>'''

		files[filename] = content.toString()
	}
}
