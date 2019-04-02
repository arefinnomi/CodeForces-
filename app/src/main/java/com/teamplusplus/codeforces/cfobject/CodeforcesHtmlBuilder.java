package com.teamplusplus.codeforces.cfobject;


public class CodeforcesHtmlBuilder {

    private final String html;

    public CodeforcesHtmlBuilder(String html) {
        String head = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\">\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
                "<meta name=\"X-Csrf-Token\" content=\"922f41b3bd68ad91c28693caba469cdc\"/>\n" +
                "<meta id=\"viewport\" name=\"viewport\" content=\"width=device-width, initial-scale=0.01\"/>\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/font-awesome.min.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link href='//fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700&subset=latin,cyrillic' rel='stylesheet' type='text/css'>\n" +
                "<link href='//fonts.googleapis.com/css?family=Cuprum&subset=latin,cyrillic' rel='stylesheet' type='text/css'>\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/prettify.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/clear.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/style.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/ttypography.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/problem-statement.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/second-level-menu.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/roundbox.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/datatable.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/table-form.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/topic.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/jquery.jgrowl.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/facebox.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/jquery.wysiwyg.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/jquery.autocomplete.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/codeforces.datepick.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/colorbox.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/jquery.drafts.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/community.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/css/sidebar-menu.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/markitup/skins/markitup/style.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "<link rel=\"stylesheet\" href=\"http://st.codeforces.com/s/75307/markitup/sets/markdown/style.css\" type=\"text/css\" charset=\"utf-8\" />\n" +
                "</head>\n" +
                "<body>";
        head = "";
        String last = "</body>\n" +
                "</html>";
        this.html = head + html + last;
    }

    public String getHtml() {
        return html;
    }
}
