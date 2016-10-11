package net.risesoft.utils.qft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public  final class QftCode {

	// 2环缺"承办"环节事项ids
	private static List<String> secondApproveItemIds = new ArrayList<String>();
	// 7环取三
	private static List<String> sevenApproveItemIds = new ArrayList<String>();
	// 需要对接的事项
	private static List<String> threeApproveItemIds = new ArrayList<String>();
	
	static{
		/**
		 * 市场科{0A009FA8-FFFF-FFFF-8CDA-148A0000004B}
		 */
		threeApproveItemIds.add("{0A009FA8-FFFF-FFFF-8CDA-148A0000004B}");
		threeApproveItemIds.add("{0A009FA8-0000-0000-72DA-20CF000003A3}");
		threeApproveItemIds.add("{0A009FA8-FFFF-FFFF-8CCA-A01400000030}");
		threeApproveItemIds.add("{7F000001-FFFF-FFFF-A669-F731FFFFFF88}");
		threeApproveItemIds.add("{0A0102A1-0000-0000-6A01-9A85FFFFFF8E}");
		threeApproveItemIds.add("{0A0102A1-0000-0000-68FD-F8BAFFFFFF8D}");
		threeApproveItemIds.add("{7F000001-FFFF-FFFF-A669-F75700000079}");
		threeApproveItemIds.add("{0A009FA8-FFFF-FFFF-8836-09C6000004FF}");
		threeApproveItemIds.add("{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}");
		threeApproveItemIds.add("{0A009FA8-0000-0000-68C9-3CDB0000004C}");
		threeApproveItemIds.add("{AC1E060B-FFFF-FFFF-A889-921DFFFFFFF5}");
		threeApproveItemIds.add("{7F000001-FFFF-FFFF-A669-F7190000007F}");
		threeApproveItemIds.add("{0A009FA8-FFFF-FFFF-8CEF-5B70FFFFFF88}");
		threeApproveItemIds.add("{0A0102A1-0000-0000-68F9-BCD4FFFFFF8C}");
		threeApproveItemIds.add("{AC1E060B-FFFF-FFFF-A884-FE18FFFFFFE8}");
		threeApproveItemIds.add("{0A0102A1-0000-0000-707B-65A500000061}");
		threeApproveItemIds.add("{0A0102A1-0000-0000-7074-D1D10000005D}");
		
		/*
		 * 物管科
		 * {AC1E060B-FFFF-FFFF-9FC8-BC8F0000006C}   龙岗区物业服务企业（三级及三级暂定）资质初审
		 * {0A009FA8-FFFF-FFFF-DD93-69E300000004}   物业服务合同备案
		 * {0A009FA8-FFFF-FFFF-DDB7-421800000049}   业主委员会成立备案及变更备案
		 * */
		threeApproveItemIds.add("{AC1E060B-FFFF-FFFF-9FC8-BC8F0000006C}");
		threeApproveItemIds.add("{0A009FA8-FFFF-FFFF-DD93-69E300000004}");
		threeApproveItemIds.add("{0A009FA8-FFFF-FFFF-DDB7-421800000049}");
		
		

		/**
		 * 业务中心
		 */
		threeApproveItemIds.add("{7F000001-FFFF-FFFF-A669-F739FFFFFF8B}");
		threeApproveItemIds.add("{7F000001-FFFF-FFFF-A669-F729FFFFFF85}");
		threeApproveItemIds.add("{7F000001-FFFF-FFFF-A669-F876FFFFFFA0}");
		threeApproveItemIds.add("{7F000001-FFFF-FFFF-A669-F720FFFFFF82}");
		threeApproveItemIds.add("{7F000001-0000-0000-7185-AA8F00000013}");
		threeApproveItemIds.add("{7F000001-0000-0000-7185-AA7900000004}");

		/**
		 * 燃气科 燃气工程供气认可备案
		 */
		threeApproveItemIds.add("{AC1E060B-FFFF-FFFF-8578-D59800000055}");

		// 建设工程项目使用袋装水泥和现场
		// 散装水泥专项基金征收
		// 新型墙体材料专项基金征收
		secondApproveItemIds.add("{7F000001-0000-0000-7185-AA7B00000005}");
		secondApproveItemIds.add("{7F000001-0000-0000-7185-AA9D0000001D}");
		secondApproveItemIds.add("{7F000001-0000-0000-7185-AA9000000014}");

		// 散装水泥专项基金反退
		// 新型墙体材料专项基金返退
		// 瓶装燃气燃气点备案
		// 瓶装燃气供应站设立许可
		// 龙岗区物业专项维修资金使用备案
		// 建筑节能专项验收备案
		// 施工图设计文件抽查
		// 瓶装燃气供应站设立审批
		sevenApproveItemIds.add("{7F000001-FFFF-FFFF-A669-F863FFFFFF9A}");
		sevenApproveItemIds.add("{7F000001-0000-0000-7185-AA9200000015}");
		sevenApproveItemIds.add("{AC1E060B-FFFF-FFFF-8565-F2690000003C}");
		sevenApproveItemIds.add("{7F000001-0000-0000-7185-AA830000000B}");
		sevenApproveItemIds.add("{7F000001-0000-0000-7185-A9D900000049}");
		sevenApproveItemIds.add("{AC1E060B-FFFF-FFFF-A933-0D7200000072}");
		sevenApproveItemIds.add("{AC1E060B-FFFF-FFFF-AA45-6F1A000004B6}");
		sevenApproveItemIds.add("{7F000001-0000-0000-7185-AA830000000B}");
	}
	
	public static String threeApproveExchange(String approveItemGUID) {
		for (Iterator<String> it = threeApproveItemIds.iterator(); it.hasNext();) {
			if (it.next().equals(approveItemGUID)) {
				return "three";
			}
		}

		for (Iterator<String> it = secondApproveItemIds.iterator(); it
				.hasNext();) {
			if (it.next().equals(approveItemGUID)) {
				return "second";
			}
		}

		for (Iterator<String> it = sevenApproveItemIds.iterator(); it.hasNext();) {
			if (it.next().equals(approveItemGUID)) {
				return "seven";
			}
		}
		return "zero";
	}
}
