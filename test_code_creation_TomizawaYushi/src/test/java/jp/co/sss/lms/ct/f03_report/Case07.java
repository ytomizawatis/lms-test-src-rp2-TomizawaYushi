package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 * @author 冨澤 雄志
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// トップページのURLにアクセスする
		goTo("http://localhost:8080/lms");

		// タイトルの判定処理
		assertEquals("ログイン | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// (初回ログイン済み受講生ユーザーの)ID, パスワードを入力
		webDriver.findElement(By.id("loginId")).clear();
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).clear();
		webDriver.findElement(By.id("password")).sendKeys("StudentAA00");

		// ログインボタンをクリック
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		// コース詳細画面が表示されるまで待つ
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.cssSelector("li.active"), "コース詳細"));

		// タイトルの判定処理
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 未提出の日の「詳細」ボタンをクリック
		webDriver.findElement(By.xpath("//tr[td//span[text()='未提出']]//input[@value='詳細']")).click();

		// セクション詳細画面が表示されるまで待つ
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.cssSelector("li.active"), "セクション詳細"));

		// タイトルの判定処理
		assertEquals("セクション詳細 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「日報【デモ】を提出する」ボタンをクリック
		webDriver.findElement(By.cssSelector("input[type='submit'][value='日報【デモ】を提出する']")).click();

		// レポート登録画面が表示されるまで待つ
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.cssSelector("h2"), "日報【デモ】"));

		// タイトルの判定処理
		assertEquals("レポート登録 | LMS", webDriver.getTitle());

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// 報告内容を入力
		webDriver.findElement(By.id("content_0")).clear();
		webDriver.findElement(By.id("content_0")).sendKeys("任意の報告内容");

		// 「提出する」ボタンをクリック
		webDriver.findElement(By.cssSelector("button[type='submit'].btn.btn-primary")).click();

		// セクション詳細画面が表示されるまで待つ
		final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.cssSelector("li.active"), "セクション詳細"));

		// ボタンのvalueの判定処理
		assertEquals("提出済み日報【デモ】を確認する", webDriver
				.findElement(By.cssSelector("input[type='submit'][value='提出済み日報【デモ】を確認する']")).getAttribute("value"));

		// エビデンスを取得
		getEvidence(new Object() {
		});
	}

}
