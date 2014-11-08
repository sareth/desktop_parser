package schedule_updater;

import org.jdom2.*;
import org.jdom2.input.*;

import java.io.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ReadRings {
	public ReadRings() {
		// read rings
		// ������ ������ InsertRingsIntoDB
		InsertDataIntoDB db = new InsertDataIntoDB();
		// ����� �������� ������� �� ������� ��� �������� ������
		db.dropTableRings();

		try {
			// ������� ����� ������
			SAXBuilder parser = new SAXBuilder();
			// ����� ������
			// TODO: ������� ������ ������ ����� ��� �������������� ������
			// �������
			FileReader fr = new FileReader(GeneratorFrame.pathToRings.getText());
			// �������� ���
			Document rDoc = parser.build(fr);
			// ������ Root element
			System.out.println("Root node of rings: " + rDoc.getRootElement().getName());
			// List child elements of root
			List<Element> temp = rDoc.getRootElement().getChildren();
			for (int i = 0; i < temp.size(); ++i) {
				System.out.print(temp.get(i).getName() + " ");
				System.out.print(temp.get(i).getAttributeValue("number") + " ");
				System.out.print(temp.get(i).getAttributeValue("begin_time") + " ");
				System.out.print(temp.get(i).getAttributeValue("end_time") + "\n");
				// �������� ������� ����� �� ��� ����
				int number = Integer.valueOf(temp.get(i).getAttributeValue("number"));
				// �������� ������� ������ ���� �� ��� ����
				String beginTime = temp.get(i).getAttributeValue("begin_time");
				// �������� ������� ����� ���� �� ��� ����
				String endTime = temp.get(i).getAttributeValue("end_time");
				int value = (100 * i) / temp.size();
				NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
				formatter.setMinimumFractionDigits(2);
				double j = (double) 100 * i / temp.size();
				GeneratorFrame.progressBar.setIndeterminate(false);
				GeneratorFrame.progressBar.setValue(value);
				GeneratorFrame.progressBar.setString(formatter.format(j) + "%");
				// insert data in base
				db.insertRings(number, beginTime, endTime);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
