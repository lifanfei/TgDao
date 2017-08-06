package com.tg.generator.sql.primary;

import com.tg.annotation.Insert;
import com.tg.exception.TgDaoException;
import com.tg.generator.model.TableMapping;
import com.tg.util.StringUtils;
import org.dom4j.Element;

import javax.lang.model.element.ExecutableElement;

/**
 * Created by twogoods on 2017/7/31.
 */
public class InsertGen extends PrimarySqlGen {
    private Insert insert;

    public InsertGen(ExecutableElement executableElement, TableMapping tableInfo, Insert insert) {
        super(executableElement, tableInfo);
        this.insert = insert;
    }

    @Override
    public void checkAnnotatedRule() {
        if (executableElement.getParameters().size() != 1) {
            throw new TgDaoException(String.format("check method %s , support only one parameter", executableElement.getSimpleName().toString()));
        }
        if (!tableInfo.getClassName().equals(executableElement.getParameters().get(0).asType().toString())) {
            throw new TgDaoException(String.format("insert param need '%s' but get '%s'", tableInfo.getClassName(),
                    executableElement.getParameters().get(0).asType().toString()));
        }
    }

    @Override
    public Element generateBaseSql(Element root) {
        Element selectElement = root.addElement("insert");
        selectElement.addAttribute("id", executableElement.getSimpleName().toString())
                .addAttribute("parameterType", tableInfo.getClassName());
        if (insert.useGeneratedKeys() && StringUtils.isEmpty(insert.keyProperty())) {
            selectElement.addAttribute("useGeneratedKeys", "true")
                    .addAttribute("keyProperty", insert.keyProperty());
        }
        selectElement.addText("insert into " + tableInfo.getTableName());
        Element columnElement = generateTrimElement(selectElement, "(", ")", ",");
        Element valuesElement = generateTrimElement(selectElement, "values (", ")", ",");
        tableInfo.getFieldToColumn().forEach((key, value) -> {
            columnElement.addElement("if")
                    .addAttribute("test", key + " != null")
                    .addText(value + ",");
            valuesElement.addElement("if")
                    .addAttribute("test", key + " != null")
                    .addText("#{" + key + "},");

        });
        return selectElement;
    }
}
