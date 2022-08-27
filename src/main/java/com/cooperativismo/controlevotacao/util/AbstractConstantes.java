package com.cooperativismo.controlevotacao.util;

import com.cooperativismo.controlevotacao.annotation.ExcludeGson;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public interface AbstractConstantes {

//	Bean Validations
	public static final String NULL = "tem que ser null";
	public static final String NOT_NULL = "é obrigatório";
	public static final String INVALID = "é inválido";

	public static final String SP_TIME_ZONE = "America/Sao_Paulo";

//  Configurações do Gson
	public static final ExclusionStrategy STRATEGY_GSON = new ExclusionStrategy() {
		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
		@Override
		public boolean shouldSkipField(FieldAttributes field) {
			return field.getAnnotation(ExcludeGson.class) != null;
		}
	};

}
