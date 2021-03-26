
package com.lyc.spark.auth.granter;

import com.lyc.spark.core.support.Kv;
import lombok.Data;

@Data
public class TokenParameter {

	private Kv args = Kv.init();

}
