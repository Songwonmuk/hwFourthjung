package net.scit.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.scit.entity.Patient;

public class TreatmentPatientServiceImpl implements TreatmentPatientService {
	List<Patient> list = new ArrayList<>();

	public TreatmentPatientServiceImpl() {
		super();
		getFile();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getFile() {
		File file = null;

		try {
			file = new File("patient.dat");
			if (!file.exists())
				return;

			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			list = (List<Patient>) ois.readObject();

			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setFile() {
		File file = null;

		try {
			file = new File("patient.dat");
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

			oos.writeObject(list);

			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean regist(Patient patient) {
		return list.add(patient);
	}

	@Override
	public Patient findByPatientId(String patientId) {
		for(Patient p : list) {
			if(p.getPatientId().equals(patientId))
				return p;
		}
		return null;
	}

	@Override
	public List<Patient> findByPatientName(String name) {
		List<Patient> newList = new ArrayList<>();
		
		for(Patient p : list) {
			if(p.getName().equals(name)) {
				newList.add(p);
			}
		}
		
		if(!newList.isEmpty())
			return newList;
		else
			return null;
	}

	@Override
	public List<Patient> selectAll() {
		if(!list.isEmpty()) {
			Collections.sort(list, (o1, o2) -> o1.getPatientId().compareTo(o2.getPatientId()));
			return list;
		}
		else
			return null;
	}
}
