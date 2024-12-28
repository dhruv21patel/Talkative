export default function Fields({type,name,val,handler,children}) {
    return <div className='h-fit w-full '>

        <label htmlFor="email" className="block text-sm h-fit w-fit font-medium text-gray-700 ">
        {children}
        </label>

        <input
        type={type}
        name={name}
        id={name}
        value={val.name}
        onChange={handler}
        required
        className="w-full border-b-2 border-black h-fit p-2 mt-1 border rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
        />
  </div>
};
