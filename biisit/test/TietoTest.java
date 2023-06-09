package  biisit.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import  biisit.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2022.04.23 11:43:03 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class TietoTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi67 */
  @Test
  public void testRekisteroi67() {    // Tieto: 67
    Tieto pitsi1 = new Tieto(); 
    assertEquals("From: Tieto line: 69", 0, pitsi1.getTunnusNro()); 
    pitsi1.rekisteroi(); 
    Tieto pitsi2 = new Tieto(); 
    pitsi2.rekisteroi(); 
    int n1 = pitsi1.getTunnusNro(); 
    int n2 = pitsi2.getTunnusNro(); 
    assertEquals("From: Tieto line: 75", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString109 */
  @Test
  public void testToString109() {    // Tieto: 109
    Tieto tieto = new Tieto(); 
    tieto.parse(" 3 | Mazza | slowthai & A$AP Rocky"); 
    assertEquals("From: Tieto line: 112", false, tieto.toString().startsWith(" 3 | Mazza | slowthai & A$AP Rocky")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse125 */
  @Test
  public void testParse125() {    // Tieto: 125
    Tieto tieto = new Tieto(); 
    tieto.parse(" 3 | Mazza | slowthai & A$AP Rocky"); 
    tieto.rekisteroi(); 
    int n = tieto.getTunnusNro(); 
    tieto.parse(""+(n+20)); 
    tieto.rekisteroi(); 
    assertEquals("From: Tieto line: 132", n+20+1, tieto.getTunnusNro()); 
  } // Generated by ComTest END
}