package com.qhc.steigenberger.util;

import java.io.IOException;
import java.text.DecimalFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Walker
 **/
public class ItemDiscountSerializer extends JsonSerializer<Double> {

	private static final DecimalFormat FORMAT = new DecimalFormat("#.##");

	@Override
	public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {

		String text = null;
		// 是否为空
		if (value != null) {
			try {
				// 格式化是否为空
				text = FORMAT.format(value);
			} catch (Exception e) {
				text = value.toString();
			}
		}
		if (text != null) {
			jsonGenerator.writeString(text);
		}
	}
	
	/*
	 * public static final void main(String[] args) { double f = 6234.2999999999;
	 * System.out.println(String.format("%.2f", f)); DecimalFormat df = new
	 * DecimalFormat("#.00"); System.out.println(df.format(f)); df = new
	 * DecimalFormat("#.##"); System.out.println(df.format(f)); }
	 */
}