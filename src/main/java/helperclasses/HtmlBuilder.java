package helperclasses;

public class HtmlBuilder {
    public class Form {
        private final String form_css_class = "edit_film_form";
        private String html; // The HTML representation of the object

        // Initialize the object with the specified post action
        public Form(String postAction) {
            html = String.format("<form class='%s' action=\"%s\" method=\"post\">\n", form_css_class, postAction);
        }

        // Add an input and an associated label
        public void addInput(String type, String label) {
            html += String.format
                    ("<label for=\"%s\">%s: </label><br>\n",
                            label.toLowerCase().replaceAll(" ", ""), label);
            html += String.format
                    ("<input type=\"%s\" id=\"%s\" name=\"%s\"><br>\n",
                            type, label.toLowerCase().replaceAll(" ", ""), label.toLowerCase().replaceAll(" ", ""));
        }

        @Override
        public String toString() {
            return html + "<input type=\"submit\" value=\"Submit\">\n</form>";
        }
    }
    public class Table {
        private final String table_css_class = "films_table";
        private String html;    // The HTML representation of the object
        private int columnCount = 0;

        public Table(String... columns) {
            html = String.format("<table class=\"%s\">\n<tr>\n", table_css_class);
            for (String column : columns) {
                html += "<th>" + column + "</th>\n";
                columnCount++;
            }
            html += "</tr>\n";
        }

        public void addRow(Object... values) {
            if (values.length != columnCount) return;

            html += "<tr>\n";
            for (Object value : values) {
                html += "<td>" + value.toString() + "</td>\n";
            }
            html += "</tr>\n";
        }
        public void addRowWithStyle(String rowStyle, Object... values) {
            if (values.length != columnCount) return;

            html += String.format("<tr style=\"%s\">\n", rowStyle);
            for (Object value : values) {
                html += "<td>" + value.toString() + "</td>\n";
            }
            html += "</tr>\n";
        }

        @Override
        public String toString() {
            return html + "</table>";
        }
    }
}

