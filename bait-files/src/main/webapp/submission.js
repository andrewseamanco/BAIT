/** Do not allow spaces in input */
function checkSpace(event)
{
   if(event.which ==32)
   {
      event.preventDefault();
      return false;
   }
}
