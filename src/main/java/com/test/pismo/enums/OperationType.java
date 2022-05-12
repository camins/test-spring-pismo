package com.test.pismo.enums;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OperationType {

	COMPRA_A_VISTA(1, "Compra à vista"){

		@Override
		public Double getValue(Double value) {
			if(value < 0) {
				return value;
			}
			return (-1)*value;
		}
		
	},
	COMPRA_PARCELADA(2, "Compra Parcelada"){

		@Override
		public Double getValue(Double value) {
			if(value < 0) {
				return value;
			}
			return (-1)*value;
		}
		
	},
    SAQUE(3, "Saque"){

		@Override
		public Double getValue(Double value) {
			if(value < 0) {
				return value;
			}
			return (-1)*value;
		}
		
	},
    PAGAMENTO(4, "Pagamento"){

		@Override
		public Double getValue(Double value) {
			if(value > 0) {
				return value;
			}
			return (-1)*value;
		}
		
	};
	
	private final Integer id;
    private final String description;
    
    private static List<OperationType> getList() {
        return Arrays.asList(OperationType.values());
    }
    
    public static OperationType findOperationById(Integer id) {
    	return getList()
    			.stream()
    			.filter(operationType -> operationType.getId() == id)
    			.findFirst()
    			.get();
    }
    
    public abstract Double getValue(Double value);
}
