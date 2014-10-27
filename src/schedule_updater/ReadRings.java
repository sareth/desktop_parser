package schedule_updater;

import org.jdom2.*;
import org.jdom2.input.*;

import java.io.*;
import java.util.List;

public class ReadRings {
	public ReadRings() {
		// read rings
		// Объект класса InsertRingsIntoDB
		InsertDataIntoDB db = new InsertDataIntoDB();
		// Метод вычищает таблицу на сервере для загрузки данных
		db.dropTableRings();

		try {
			// создаем новый парсер
			SAXBuilder parser = new SAXBuilder();
			// берем файлик
			// TODO: сделать диалог выбора файла или автоматическое чтение
			// конфига
			FileReader fr = new FileReader("rings.xml");
			// получаем док
			Document rDoc = parser.build(fr);
			// парсим Root element
			System.out.println("Root node of rings: " + rDoc.getRootElement().getName());
			// List child elements of root
			List<Element> temp = rDoc.getRootElement().getChildren();
			for (int i = 0; i < temp.size(); ++i) {
				System.out.print(temp.get(i).getName() + " ");
				System.out.print(temp.get(i).getAttributeValue("number") + " ");
				System.out.print(temp.get(i).getAttributeValue("begin_time") + " ");
				System.out.print(temp.get(i).getAttributeValue("end_time") + "\n");
				// получаем атрибут номер из рут ноды
				int number = Integer.valueOf(temp.get(i).getAttributeValue("number"));
				// получаем атрибут начала пары из рут ноды
				String beginTime = temp.get(i).getAttributeValue("begin_time");
				// получаем атрибут конца пары из рут ноды
				String endTime = temp.get(i).getAttributeValue("end_time");

				// insert data in base
				db.insertRings(number, beginTime, endTime);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
