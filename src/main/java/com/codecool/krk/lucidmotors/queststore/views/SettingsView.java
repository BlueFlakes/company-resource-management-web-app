package com.codecool.krk.lucidmotors.queststore.views;

import com.codecool.krk.lucidmotors.queststore.dao.StylesheetsDao;
import com.codecool.krk.lucidmotors.queststore.exceptions.DaoException;
import com.codecool.krk.lucidmotors.queststore.handlers.helpers.Cookie;
import com.codecool.krk.lucidmotors.queststore.models.Activity;
import com.codecool.krk.lucidmotors.queststore.models.Stylesheet;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;
import java.util.Map;

public class SettingsView {

    private Map<String, String> formData;
    private HttpExchange httpExchange;

    public SettingsView(Map<String, String> formData, HttpExchange httpExchange) {
        this.formData = formData;
        this.httpExchange = httpExchange;
    }

    public Activity getActivity() throws DaoException {
        if (this.formData.containsKey("stylesheet")) {
            Stylesheet stylesheet = StylesheetsDao.getDao().getStylesheet(this.formData.get("stylesheet"));
            if(stylesheet != null) {
                Cookie.setCookieNoMaxAge(this.httpExchange, "style_path", stylesheet.getPath());
            }
            return new Activity(302, "/");
        } else {
            return getStyles();
        }
    }

    private Activity getStyles() throws DaoException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/settings.twig");
        JtwigModel model = JtwigModel.newModel();

        List<Stylesheet> stylesheets = StylesheetsDao.getDao().getAllStylesheets();
        model.with("stylesheets", stylesheets);
        response = template.render(model);
        return new Activity(200, response);
    }
}
